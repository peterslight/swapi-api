package com.peterstev.trivago

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.peterstev.domain.models.Result
import com.peterstev.trivago.mock.utils.MockGen
import com.peterstev.trivago.mock.viewmodels.MockHomeViewModel
import com.peterstev.trivago.utilities.observeOnce
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeVmTest {

    private lateinit var mock: MockGen
    private val characterName = "Chewbacca"
    private val characterGender = "male"

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Inject
    lateinit var viewModel: MockHomeViewModel

    @Before
    fun setup() {
        mock = MockGen()
        hiltRule.inject()
    }

    @Test
    fun assert_view_model_is_injected() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun assert_get_character_returns_character() {
        val character = mock.getCharacter()
        assertThat(character.name).isEqualTo(characterName)
        assertThat(character.gender).isEqualTo(characterGender)
    }

    @Test
    fun assert_character_live_data_contains_data() {
        val character = mock.getCharacter()
        coroutineRule.runBlockingTest {
            viewModel.setCharacter(character)
            viewModel.searchResultObservable.observeOnce {
                assertThat(it.data).isNotNull()
            }
        }
    }

    @Test
    fun assert_character_live_data_equals_character_object() {
        val character = mock.getCharacter()
        coroutineRule.runBlockingTest {
            viewModel.setCharacter(character)
            viewModel.searchResultObservable.observeOnce {
                assertThat(it.data).isEqualTo(
                    Result(
                        birthYear = character.birthYear,
                        created = character.created,
                        films = character.films,
                        height = character.height,
                        gender = character.gender,
                        homeworld = character.homeworld,
                        skinColor = character.skinColor,
                        eyeColor = character.eyeColor,
                        name = character.name,
                        species = character.species
                    )
                )
            }
        }
    }

}