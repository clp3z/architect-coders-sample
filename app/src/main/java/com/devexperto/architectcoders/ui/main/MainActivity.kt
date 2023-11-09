package com.devexperto.architectcoders.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.devexperto.architectcoders.databinding.ActivityMainBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter by lazy { MainPresenter(MoviesRepository(this), lifecycleScope) }
    private lateinit var viewBinding: ActivityMainBinding

    private val moviesAdapter = MoviesAdapter {
        presenter.onMovieClick(it)
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.recycler.adapter = moviesAdapter

        presenter.onCreate(this)
    }

    override fun showProgressView() {
        viewBinding.progressView.isVisible = true
    }

    override fun hideProgressView() {
        viewBinding.progressView.isVisible = false
    }

    override fun updateMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    override fun navigateToDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, movie)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
