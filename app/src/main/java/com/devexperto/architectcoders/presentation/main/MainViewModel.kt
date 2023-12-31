package com.devexperto.architectcoders.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.framework.toError
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) : ViewModel() {

    data class ViewState(
        var isLoading: Boolean = true,
        val movies: List<com.devexperto.architectcoders.domain.Movie> = emptyList(),
        val error: com.devexperto.architectcoders.domain.Error? = null
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
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

            val error = requestPopularMoviesUseCase()
            _viewState.update { it.copy(isLoading = false, error = error) }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase) as T
}
