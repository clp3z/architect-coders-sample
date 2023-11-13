package com.devexperto.architectcoders.ui.detail

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.getParcelableExtraCompat
import kotlinx.coroutines.launch

class DetailViewModel(private val intent: Intent) : ViewModel() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    data class ViewState(
        val toolbarTitle: String = "",
        val toolbarImage: String = "",
        val movie: Movie? = null
    )

    private var _viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val viewState: LiveData<ViewState> get() {
        if (_viewState.value?.movie == null) {
            refresh()
        }
        return _viewState
    }

    private fun refresh() {
        viewModelScope.launch {
            intent.getParcelableExtraCompat<Movie>(MOVIE)?.let {
                _viewState.value = ViewState(
                    toolbarTitle = it.title,
                    toolbarImage = "https://image.tmdb.org/t/p/w780${it.backdropPath ?: it.posterPath}",
                    movie = it
                )
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val intent: Intent) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(intent) as T
}
