package com.devexperto.architectcoders.ui.main

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.devexperto.architectcoders.databinding.ActivityMainBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.detail.DetailActivity
import com.devexperto.architectcoders.ui.detail.DetailViewModel.Companion.MOVIE

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(MoviesRepository(this)) }
    private lateinit var viewBinding: ActivityMainBinding

    private val moviesAdapter = MoviesAdapter {
        viewModel.onMovieClick(it)
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.recycler.adapter = moviesAdapter

        viewModel.viewState.observe(this, ::updateView)
    }

    private fun updateView(viewState: MainViewModel.ViewState) {
        viewBinding.progressView.isVisible = viewState.isLoading
        moviesAdapter.submitList(viewState.movies)

        viewState.navigateToDetail?.let(::navigateToDetail)
    }

    private fun navigateToDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }
}
