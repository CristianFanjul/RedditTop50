package com.example.reddittop50.usecases

import android.accounts.NetworkErrorException
import com.example.reddittop50.SampleDataProvider
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.domain.IRedditRepository
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.QueryParams
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MockitoGetArticlesTest {

    private lateinit var articlesUseCase: GetArticlesUseCase

    @Mock
    private lateinit var repository: IRedditRepository

    @Before
    fun setup() {
        articlesUseCase = GetArticlesUseCase(repository)
    }

    @Test
    fun getAllMoviesFromUseCaseTest() {
        runBlocking {
            `when`(repository.requestArticles(QueryParams()))
                .thenReturn(Result.Success(SampleDataProvider.getArticlesSample()))

            val queryParams = QueryParams()
            val response = articlesUseCase.invoke(queryParams)
            verify(repository).requestArticles(queryParams)
            verifyNoMoreInteractions(repository)
            if (response is Result.Success) {
                print("Response: " + response.content)
            }
        }
    }

    @Test
    fun `error should contain message`() {
        runBlocking {
            `when`(repository.requestArticles(QueryParams()))
                .thenReturn(Result.Error(NetworkErrorException("Failure")))

            val result = articlesUseCase.invoke(QueryParams())
            assert(result is Result.Error)

            if (result is Result.Error) {
                Assert.assertNotNull(result.exception)
                assert(result.toString().isNotEmpty())
                print(result.toString())
            }
        }
    }
}