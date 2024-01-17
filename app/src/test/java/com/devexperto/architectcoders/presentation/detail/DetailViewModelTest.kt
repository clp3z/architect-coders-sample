package com.devexperto.architectcoders.presentation.detail

import app.cash.turbine.test
import com.devexperto.architectcoders.rules.CoroutinesTestRule
import com.devexperto.architectcoders.testShared.sampleMovie
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var requestMovieUseCase: RequestMovieUseCase

    @Mock
    lateinit var switchFavoriteUseCase: SwitchFavoriteUseCase

    private lateinit var viewModel: DetailViewModel

    private val id = 1
    private val movie = sampleMovie.copy(id = id, isFavorite = false)

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        viewModel = buildViewModel()

        viewModel.onViewReady(id)

        viewModel.viewState.test {
            assertEquals(DetailViewModel.ViewState(), awaitItem())
            val result = awaitItem()
            assertEquals(DetailViewModel.ViewState(movie = movie), result)
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        viewModel = buildViewModel()
        viewModel.onViewReady(id)

        viewModel.onFavoriteClicked()
        runCurrent()

        verify(switchFavoriteUseCase).invoke(movie)
    }

    // TODO
    @Test
    fun `Favorite action calls the corresponding use case alternative`() = runTest {
        viewModel = buildViewModel()
        viewModel.onViewReady(id)

        viewModel.onFavoriteClicked()

        viewModel.viewState.test {
            assertEquals(DetailViewModel.ViewState(), awaitItem())
            assertEquals(DetailViewModel.ViewState(movie = movie), awaitItem())
            assertEquals(DetailViewModel.ViewState(movie = movie.copy(isFavorite = true)), awaitItem())
            cancel()
        }
    }

    private fun buildViewModel(): DetailViewModel {
        whenever(requestMovieUseCase(id)).thenReturn(flowOf(movie))
        return DetailViewModel(requestMovieUseCase, switchFavoriteUseCase)
    }
}
