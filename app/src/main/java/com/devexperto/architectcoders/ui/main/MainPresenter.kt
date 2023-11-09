package com.devexperto.architectcoders.ui.main

import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val moviesRepository: MoviesRepository,
    private val coroutineScope: CoroutineScope
) {

    interface View {
        fun showProgressView()
        fun hideProgressView()
        fun updateMovies(movies: List<Movie>)
        fun navigateToDetail(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view

        coroutineScope.launch {
            view.showProgressView()
            view.updateMovies(moviesRepository.findPopularMovies().results)
            view.hideProgressView()
        }
    }

    fun onMovieClick(movie: Movie) {
        view?.navigateToDetail(movie)
    }

    fun onDestroy() {
        view = null
    }
}
