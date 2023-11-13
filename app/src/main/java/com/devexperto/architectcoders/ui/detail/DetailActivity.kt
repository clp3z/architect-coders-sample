package com.devexperto.architectcoders.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devexperto.architectcoders.databinding.ActivityDetailBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.loadUrl

class DetailActivity : AppCompatActivity() {

    private val viewModel by lazy { DetailViewModel(intent) }
    private lateinit var viewBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel.viewState.observe(this, ::updateView)
    }

    private fun updateView(viewState: DetailViewModel.ViewState) {
        viewBinding.movieDetailToolbar.title = viewState.toolbarTitle
        viewBinding.movieDetailImage.loadUrl(viewState.toolbarImage)

        viewState.movie?.let(::setMovie)
    }

    private fun setMovie(movie: Movie) {
        viewBinding.movieDetailSummary.text = movie.overview
        viewBinding.movieDetailInfo.setMovie(movie)
    }
}
