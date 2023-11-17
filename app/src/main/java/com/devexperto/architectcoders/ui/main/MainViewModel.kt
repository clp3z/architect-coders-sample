package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    data class ViewState(
        var isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    fun onViewReady() {
        viewModelScope.launch {
            _viewState.value = ViewState(isLoading = true)
            _viewState.value = ViewState(movies = moviesRepository.findPopularMovies().results)
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
