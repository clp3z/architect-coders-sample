package com.devexperto.architectcoders.ui.detail

import android.content.Intent
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.getParcelableExtraCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DetailPresenter(private val intent: Intent, private val coroutineScope: CoroutineScope) {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    interface View {
        fun setToolbarTitle(title: String)
        fun setToolbarImage(imageUrl: String)
        fun setMovie(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view

        coroutineScope.launch {
            intent.getParcelableExtraCompat<Movie>(MOVIE)?.let {
                view.setToolbarTitle(it.title)

                val background = it.backdropPath ?: it.posterPath
                view.setToolbarImage("https://image.tmdb.org/t/p/w780$background")

                view.setMovie(it)
            }
        }
    }

    fun onDestroy() {
        view = null
    }
}
