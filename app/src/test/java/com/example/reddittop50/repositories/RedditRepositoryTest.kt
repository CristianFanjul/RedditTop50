package com.example.reddittop50.repositories

import com.example.reddittop50.SampleDataProvider
import com.example.reddittop50.domain.IRedditDataSource
import com.example.reddittop50.domain.RedditRepository
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.QueryParams
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RedditRepositoryTest {

    private lateinit var repository: RedditRepository

    @Mock
    private lateinit var redditDataSource: IRedditDataSource

    @Before
    fun setup() {
        repository = RedditRepository(redditDataSource)
    }

    @Test
    fun `request Articles returns items`() {
        given {
            runBlocking { redditDataSource.requestArticles(QueryParams()) }
        }.willReturn { Result.Success(SampleDataProvider.getArticlesSample()) }

        runBlocking {
            repository.requestArticles(QueryParams())
            verify(redditDataSource).requestArticles(QueryParams())
        }

        verifyNoMoreInteractions(redditDataSource)
    }

    @Test
    fun `query params is limit 10 and gets up to 10 items`() {
        val params = QueryParams(10)
        given {
            runBlocking { redditDataSource.requestArticles(params) }
        }.willReturn { Result.Success(SampleDataProvider.getArticlesWithLimit(params.limit)) }

        runBlocking {
            val result = repository.requestArticles(params)
            if (result is Result.Success) {
                Assert.assertTrue(result.content.data?.children?.size!! <= 10)
                print("Total items: ${result.content.data?.children?.size}")
            }

            verify(redditDataSource).requestArticles(params)
        }

        verifyNoMoreInteractions(redditDataSource)
    }
}