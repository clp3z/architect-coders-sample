package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devexperto.architectcoders.model.database.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(movie: Movie) : ViewModel() {

    data class ViewState(val movie: Movie)

    private var _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState(movie))
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movie: Movie) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(movie) as T
}
