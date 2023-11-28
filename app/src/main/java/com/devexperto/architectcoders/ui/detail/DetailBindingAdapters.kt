package com.devexperto.architectcoders.ui.detail

import androidx.databinding.BindingAdapter
import com.devexperto.architectcoders.model.database.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.bindMovie(movie: Movie?) {
    movie?.let { setMovie(it) }
}
