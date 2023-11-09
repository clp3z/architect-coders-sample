package com.devexperto.architectcoders.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devexperto.architectcoders.databinding.ActivityDetailBinding
import com.devexperto.architectcoders.model.Movie

class DetailActivity : AppCompatActivity() {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtraCompat<Movie>(MOVIE)?.run {
            binding.movieDetailToolbar.title = title

            val background = backdropPath ?: posterPath
            binding.movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$background")

            binding.movieDetailSummary.text = overview

            binding.movieDetailInfo.setMovie(this)
        }
    }
}
