package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    data class ViewState(
        var isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
        val navigateToDetail: Movie? = null
    )

    private val _viewState = MutableLiveData(ViewState())
    val viewState: LiveData<ViewState> get() {
        if (_viewState.value?.movies?.isEmpty() == true) {
            refresh()
        }
        return _viewState
    }

    private fun refresh() {
        viewModelScope.launch {
            _viewState.value = ViewState(isLoading = true)
            _viewState.value = ViewState(movies = moviesRepository.findPopularMovies().results)
        }
    }

    fun onMovieClick(movie: Movie) {
        _viewState.value = _viewState.value?.copy(navigateToDetail = movie)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(moviesRepository) as T
}
