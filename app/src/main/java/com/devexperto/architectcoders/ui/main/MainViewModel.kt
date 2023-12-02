package com.devexperto.architectcoders.ui.main

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

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    data class ViewState(
        var isLoading: Boolean = true,
        val movies: List<Movie> = emptyList(),
        val error: Error? = null
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.popularMovies
                .catch { throwable ->
                    _viewState.update { it.copy(error = throwable.toError()) }
                }
                .collect { movies ->
                    _viewState.update { it.copy(isLoading = false, movies = movies) }
                }
        }
    }

    fun onViewReady() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }

            val error = moviesRepository.requestPopularMovies()
            _viewState.update { it.copy(isLoading = false, error = error) }
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
