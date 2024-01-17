package com.devexperto.architectcoders.presentation.main

import app.cash.turbine.test
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.presentation.fakes.FakeLocalDataSource
import com.devexperto.architectcoders.presentation.fakes.FakeLocationDataSource
import com.devexperto.architectcoders.presentation.fakes.FakePermissionChecker
import com.devexperto.architectcoders.presentation.fakes.FakeRemoteDataSource
import com.devexperto.architectcoders.presentation.main.MainViewModel.ViewState
import com.devexperto.architectcoders.rules.CoroutinesTestRule
import com.devexperto.architectcoders.testShared.sampleMovie
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MainIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: MainViewModel

    @Test
    fun `Data is loaded from server when local source is empty`() = runTest {
        val remoteMovies = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        viewModel = buildViewModel(remoteMovies)

        viewModel.onViewReady()

        viewModel.viewState.test {
            assertEquals(ViewState(), awaitItem())
            assertEquals(ViewState(movies = emptyList(), isLoading = true), awaitItem())
            assertEquals(ViewState(movies = emptyList(), isLoading = false), awaitItem())
            assertEquals(ViewState(movies = remoteMovies, isLoading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Data is loaded from local source when available`() = runTest {
        val remoteMovies = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        val localMovies = listOf(sampleMovie.copy(7), sampleMovie.copy(8))
        viewModel = buildViewModel(remoteMovies, localMovies)

        viewModel.onViewReady()

        viewModel.viewState.test {
            assertEquals(ViewState(), awaitItem())
            assertEquals(ViewState(movies = localMovies), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun buildViewModel(
        remoteMovies: List<Movie> = emptyList(),
        localMovies: List<Movie> = emptyList()
    ): MainViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)

        val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteMovies }
        val localDataSource = FakeLocalDataSource().apply { movies.value = localMovies }
        val moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)

        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)

        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}
