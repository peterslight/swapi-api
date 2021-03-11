package com.peterstev.trivago

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.peterstev.trivago.mock.utils.MockGen
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.peterstev.domain.models.Planet
import com.peterstev.domain.models.Specie
import com.peterstev.trivago.mock.viewmodels.MockDetailViewModel
import com.peterstev.trivago.utilities.Status
import com.peterstev.trivago.utilities.observeOnce
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DetailVmTest {

    private lateinit var mock: MockGen
    private val planetName = "Kashyyyk"
    private val filmTitle = "A New Hope"
    private val speciesName = "Droid"

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Inject
    lateinit var viewModel: MockDetailViewModel

    @Before
    fun setup() {
        mock = MockGen()
        hiltRule.inject()
    }

    @Test
    fun assert_get_film_returns_film() {
        val film = mock.getFilm()
        assertThat(film.title).isEqualTo(filmTitle)
    }

    @Test
    fun assert_get_planet_returns_planet() {
        val planet = mock.getPlanet()
        assertThat(planet.name).isEqualTo(planetName)
    }

    @Test
    fun assert_get_specie_returns_specie() {
        val specie = mock.getSpecies()
        assertThat(specie.name).isEqualTo(speciesName)
    }

    @Test
    fun assert_view_model_is_injected() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun assert_film_live_data_contains_data() {
        val film = mock.getFilm()
        coroutineRule.runBlockingTest {
            viewModel.setFilms(film)
            viewModel.filmsObservable.observeOnce {
                assertThat(it.data).isNotEmpty()
            }
        }
    }

    @Test
    fun assert_specie_live_data_contains_data() {
        val specie = mock.getSpecies()
        coroutineRule.runBlockingTest {
            viewModel.setSpecie(specie)
            viewModel.specieObservable.observeOnce {
                assertThat(it.data).isNotNull()
                assertThat(it.data).isEqualTo(
                    Specie(
                        averageLifespan = specie.averageLifespan,
                        classification = specie.classification,
                        created = specie.created,
                        designation = specie.designation,
                        homeworld = specie.homeworld,
                        language = specie.language,
                        name = specie.name
                    )
                )
            }
        }
    }

    @Test
    fun assert_planet_live_data_contains_data() {
        val planet = mock.getPlanet()
        coroutineRule.runBlockingTest {
            viewModel.setPlanet(planet)
            viewModel.planetObservable.observeOnce {
                assertThat(it.data).isNotNull()
                assertThat(it.data).isEqualTo(
                    Planet(
                        created = planet.created,
                        name = planet.name,
                        population = planet.population
                    )
                )
            }
        }
    }

    @Test
    fun assert_film_live_data_contains_only_one_item() {
        val film = mock.getFilm()
        coroutineRule.runBlockingTest {
            viewModel.setFilms(film)
            viewModel.filmsObservable.observeOnce {
                assertThat(it.data).hasSize(1)
            }
        }
    }

    @Test
    fun assert_film_live_data_contains_multiple_items() {
        val film = mock.getFilm()
        coroutineRule.runBlockingTest {
            viewModel.setFilms(film)
            viewModel.setFilms(film)
            viewModel.setFilms(film)
            viewModel.filmsObservable.observeOnce {
                assertThat(it.data).hasSize(3)
            }
        }
    }

    @Test
    fun assert_on_clear_removes_existing_data() {
        val film = mock.getFilm()
        coroutineRule.runBlockingTest {
            viewModel.setFilms(film)
            viewModel.filmsObservable.observeOnce {
                assertThat(it.data).hasSize(1)
            }
            viewModel.cleanUp()
            viewModel.filmsObservable.observeOnce {
                assertThat(it.data).hasSize(0)
            }
        }
    }

    @Test
    fun assert_on_clear_sets_live_data_to_idle_state() {
        val film = mock.getFilm()
        coroutineRule.runBlockingTest {
            viewModel.setFilms(film)
            viewModel.cleanUp()
            viewModel.filmsObservable.observeOnce {
                assertThat(it.status).isEqualTo(Status.IDLE)
            }
        }
    }
}