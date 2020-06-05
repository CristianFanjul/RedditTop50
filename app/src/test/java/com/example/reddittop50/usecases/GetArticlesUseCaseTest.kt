package com.example.reddittop50.usecases

import com.example.reddittop50.UnitTestsRepository
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.QueryParams
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetArticlesUseCaseTest {

    private lateinit var articlesUseCase: GetArticlesUseCase
    private val repository = UnitTestsRepository()


    @Before
    fun setup() {
        articlesUseCase = GetArticlesUseCase(repository)
    }

    @Test
    fun getAllMoviesFromUseCaseTest() {
        repository.error = false
        val result = runBlocking {
            articlesUseCase.invoke(QueryParams())
        }
        assert(result is Result.Success)

        if (result is Result.Success) {
            checkNotNull(result.content)
            checkNotNull(result.content.data)
            checkNotNull(result.content.data?.children)

            Assert.assertTrue(result.content.data!!.children!!.isNotEmpty())
        }
    }

    @Test
    fun `error should contain message`() {
        repository.error = true
        val result = runBlocking { articlesUseCase.invoke(QueryParams()) }
        assert(result is Result.Error)

        if (result is Result.Error) {
            Assert.assertNotNull(result.exception)
            assert(result.toString().isNotEmpty())
        }
    }
}