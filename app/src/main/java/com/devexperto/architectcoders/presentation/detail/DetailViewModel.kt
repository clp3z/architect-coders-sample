package com.devexperto.architectcoders.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.toError
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val requestMovieUseCase: RequestMovieUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) : ViewModel() {

    data class ViewState(
        val movie: Movie? = null,
        val error: Error? = null
    )

    private var _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    fun onViewReady(movieId: Int) {
        viewModelScope.launch {
            requestMovieUseCase(movieId)
                .catch { throwable -> _viewState.update { it.copy(error = throwable.toError()) } }
                .collect { movie -> _viewState.update { ViewState(movie) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            viewState.value.movie?.let { movie ->
                val error = switchFavoriteUseCase(movie)
                _viewState.update { it.copy(movie = movie, error = error) }
            }
        }
    }
}
