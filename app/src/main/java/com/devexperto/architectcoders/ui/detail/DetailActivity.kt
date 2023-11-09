package com.devexperto.architectcoders.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.devexperto.architectcoders.databinding.ActivityDetailBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.loadUrl

class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    private val presenter by lazy { DetailPresenter(intent, lifecycleScope) }
    private lateinit var viewBinding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        presenter.onCreate(this)
    }

    override fun setToolbarTitle(title: String) {
        viewBinding.movieDetailToolbar.title = title
    }

    override fun setToolbarImage(imageUrl: String) {
        viewBinding.movieDetailImage.loadUrl(imageUrl)
    }

    override fun setMovie(movie: Movie) {
        viewBinding.movieDetailSummary.text = movie.overview
        viewBinding.movieDetailInfo.setMovie(movie)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
