package com.peterstev.trivago

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.peterstev.domain.models.Result
import com.peterstev.trivago.adapters.HomeAdapter
import com.peterstev.trivago.mock.utils.MockGen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeAdapterTest {

    @get:Rule
    val testExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()


    private lateinit var adapter: HomeAdapter
    private lateinit var mock: MockGen
    private var resultListSize: Number? = null

    @Before
    fun setup() {
        mock = MockGen()
        val people = mock.getPeople()
        resultListSize = people.results?.size
        adapter = HomeAdapter(people.results as MutableList<Result>, null)
    }

    @Test
    fun assert_adapter_is_not_null() {
        assertThat(adapter).isNotNull()
    }

    @Test
    fun assert_adapter_count_is_greater_than_zero() {
        assertThat(adapter.itemCount).isGreaterThan(0)
    }

    @Test
    fun assert_adapter_count_is_equals_result_size() {
        assertThat(adapter.itemCount).isEqualTo(resultListSize)
    }

    @Test
    fun assert_adapter_data_is_updated() {
        coroutineRule.runBlockingTest {
            val result = mock.getPeople().results
            adapter = HomeAdapter(result as MutableList<Result>, null)
            val itemOne = result[0]
            result.remove(itemOne)
            adapter.updateList(result)
            assertThat(adapter.itemCount).isLessThan(resultListSize?.toInt())
        }
    }
}