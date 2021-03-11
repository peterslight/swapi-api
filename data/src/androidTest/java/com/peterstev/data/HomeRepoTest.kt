package com.peterstev.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.peterstev.data.repository.SearchRepository
import com.peterstev.domain.models.People
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: SearchRepository

    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        server = MockWebServer()
        server.start(8080)
        hiltRule.inject()
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun read_success_json_file() {
        val response = FileReader.readStringFromFile("response_success.json")
        assertNotNull(response)
    }

    @Test
    fun assert_repo_is_not_null() {
        assertNotNull(repository)
    }

    @Test
    fun read_error_json_file() {
        val response = FileReader.readStringFromFile("response_error.json")
        assertNotNull(response)
    }

    @Test
    fun assert_success_equals_response_success_json() {
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(FileReader.readStringFromFile("response_success.json"))
            server.enqueue(mockResponse)
            val result = mockResponse.body?.readUtf8()
            val response = repository.searchCharacter("luke")
            assertEquals(result?.let {
                parse(it)
            }, response)
        }
    }

    @Test
    fun assert_success_result_size_equals_one() {
        runBlocking {
            val response = repository.searchCharacter("luke")
            assertEquals(
                response.results!!.size,
                1
            )
        }
    }

    @Test
    fun assert_failure_equals_response_error_json() {
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(FileReader.readStringFromFile("response_error.json"))
            server.enqueue(mockResponse)
            val result = mockResponse.body?.readUtf8()
            val response = repository.searchCharacter("luke_error")
            assertEquals(
                parse(result!!),
                response
            )
        }
    }

    @Test
    fun assert_failure_result_size_equals_zero() {
        runBlocking {
            val response = repository.searchCharacter("luke_error")
            assertEquals(
                response.results!!.size,
                0
            )
        }
    }

    private fun parse(json: String): People {
        return Gson().fromJson(json, People::class.java)
    }
}