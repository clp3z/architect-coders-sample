package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.model.database.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    data class ViewState(
        var isLoading: Boolean = true,
        val movies: List<Movie> = emptyList(),
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.popularMovies.collect {
                _viewState.value = viewState.value.copy(isLoading = false, movies = it)
            }
        }
    }

    fun onViewReady() {
        viewModelScope.launch {
            _viewState.value = viewState.value.copy(isLoading = true)
            moviesRepository.requestPopularMovies()
            _viewState.value = viewState.value.copy(isLoading = false)
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
