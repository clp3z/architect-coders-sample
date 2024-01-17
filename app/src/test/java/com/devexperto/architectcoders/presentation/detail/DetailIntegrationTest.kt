package com.devexperto.architectcoders.presentation.detail

import app.cash.turbine.test
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.presentation.detail.DetailViewModel.ViewState
import com.devexperto.architectcoders.presentation.fakes.FakeLocalDataSource
import com.devexperto.architectcoders.presentation.fakes.FakeLocationDataSource
import com.devexperto.architectcoders.presentation.fakes.FakePermissionChecker
import com.devexperto.architectcoders.presentation.fakes.FakeRemoteDataSource
import com.devexperto.architectcoders.rules.CoroutinesTestRule
import com.devexperto.architectcoders.testShared.sampleMovie
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DetailIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: DetailViewModel

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val movie = sampleMovie.copy(2, isFavorite = false)
        val localMovies = listOf(sampleMovie.copy(1), movie)
        viewModel = buildViewModel(localMovies = localMovies)
        viewModel.onViewReady(2)

        viewModel.onFavoriteClicked()

        viewModel.viewState.test {
            assertEquals(ViewState(), awaitItem())
            assertEquals(ViewState(movie = movie), awaitItem())
            assertEquals(ViewState(movie = movie.copy(isFavorite = true)), awaitItem())
            cancel()
        }
    }

    private fun buildViewModel(
        remoteMovies: List<Movie> = emptyList(),
        localMovies: List<Movie> = emptyList()
    ): DetailViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)

        val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteMovies }
        val localDataSource = FakeLocalDataSource().apply { movies.value = localMovies }
        val moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)

        val requestMoviesUseCase = RequestMovieUseCase(moviesRepository)
        val switchFavoriteUseCase = SwitchFavoriteUseCase(moviesRepository)

        return DetailViewModel(requestMoviesUseCase, switchFavoriteUseCase)
    }
}
