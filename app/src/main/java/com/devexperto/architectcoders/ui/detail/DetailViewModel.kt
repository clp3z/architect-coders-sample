package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.Error
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.toError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    data class ViewState(
        val movie: Movie? = null,
        val error: Error? = null
    )

    private var _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    fun onViewReady(movieId: Int) {
        viewModelScope.launch {
            moviesRepository.requestMovieById(movieId)
                .catch { throwable ->
                    _viewState.update { it.copy(error = throwable.toError()) }
                }
                .collect { movie ->
                    _viewState.update { ViewState(movie) }
                }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            viewState.value.movie?.let { movie ->
                val error = moviesRepository.switchFavorite(movie)
                _viewState.update { it.copy(error = error) }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(moviesRepository) as T
}
