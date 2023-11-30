package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.model.database.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    data class ViewState(val movie: Movie? = null)

    private var _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    fun onViewReady(movieId: Int) {
        viewModelScope.launch {
            moviesRepository.requestMovieById(movieId).collect { movie ->
                _viewState.value = ViewState(movie)
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            viewState.value.movie?.let { movie ->
                moviesRepository.switchFavorite(movie)
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
