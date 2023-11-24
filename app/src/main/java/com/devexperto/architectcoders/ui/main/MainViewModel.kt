package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    companion object {
        private const val networkRequestTime = 1000L
    }

    /*
     * Notice that isLoading should be true only if there is a different between the old movies
     * list and new one.
     */
    data class ViewState(
        var isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    fun onViewReady() {
        viewModelScope.launch {
            val movies = moviesRepository.findPopularMovies().results
            val didMoviesChange = viewState.value.movies != movies

            _viewState.value = viewState.value.copy(isLoading = didMoviesChange)
            if (didMoviesChange) delay(networkRequestTime)
            _viewState.value = viewState.value.copy(isLoading = false, movies = movies)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(moviesRepository) as T
}
