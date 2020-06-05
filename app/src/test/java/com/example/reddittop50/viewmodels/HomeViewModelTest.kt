package com.example.reddittop50.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.reddittop50.AndroidTest
import com.example.reddittop50.SampleDataProvider
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.QueryParams
import com.example.reddittop50.ui.home.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : AndroidTest() {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var articlesUseCase: GetArticlesUseCase

    @Before
    fun setup() {
        viewModel = HomeViewModel(articlesUseCase)
    }

    @Test
    fun `test article request updates live data`() {
        val data = SampleDataProvider.getArticlesSample()
        val params = QueryParams(25, null)
        `when`(
            runBlocking {
                articlesUseCase.invoke(params)
            }
        ).thenReturn(Result.Success(data))

        viewModel.items.observeForever {
            with(it!!) {
                Assert.assertTrue(size == 25)
                print("Size is $size")
            }
        }

        runBlocking { viewModel.loadInitialArticles() }

        runBlocking {
            Mockito.verify(articlesUseCase, Mockito.times(1)).invoke(params);
        }
        print("Invocations to mock: ${Mockito.mockingDetails(articlesUseCase).invocations.size}")


//        verify(articlesUseCase).invoke(params)
//        verifyNoMoreInteractions(articlesUseCase)
    }
}