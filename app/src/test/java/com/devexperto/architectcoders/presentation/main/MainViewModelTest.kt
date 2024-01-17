@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.devexperto.architectcoders.presentation.main

import app.cash.turbine.test
import com.devexperto.architectcoders.rules.CoroutinesTestRule
import com.devexperto.architectcoders.testShared.sampleMovie
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    private lateinit var viewModel: MainViewModel

    private val movies = listOf(sampleMovie.copy(1))

    @Test
    fun `ViewState is updated with current cached content immediately`() = runTest {
        viewModel = buildViewModel()

        val result = mutableListOf<MainViewModel.ViewState>()
        val job = launch { viewModel.viewState.toList(result) }
        runCurrent()
        job.cancel()

        assertEquals(MainViewModel.ViewState(movies = movies), result[0])
    }

    @Test
    fun `ViewState is updated with current cached content immediately (Turbine)`() = runTest {
        viewModel = buildViewModel()

        viewModel.viewState.test {
            assertEquals(MainViewModel.ViewState(), awaitItem())
            assertEquals(MainViewModel.ViewState(movies = movies), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is displayed when screen starts and hidden when it finishes requesting movies`() = runTest {
        viewModel = buildViewModel()

        viewModel.onViewReady()

        viewModel.viewState.test {
            assertEquals(MainViewModel.ViewState(), awaitItem())
            assertEquals(MainViewModel.ViewState(movies = movies), awaitItem())
            assertEquals(MainViewModel.ViewState(movies = movies, isLoading = true), awaitItem())
            assertEquals(MainViewModel.ViewState(movies = movies, isLoading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Popular movies are requested when UI screen starts`() = runTest {
        viewModel = buildViewModel()

        viewModel.onViewReady()
        runCurrent()

        verify(requestPopularMoviesUseCase).invoke()
    }

    private fun buildViewModel(): MainViewModel {
        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(movies))
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}
