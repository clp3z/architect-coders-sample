package com.devexperto.architectcoders.ui.detail

import androidx.databinding.BindingAdapter

@BindingAdapter("movie")
fun MovieDetailInfoView.bindMovie(movie: com.devexperto.architectcoders.domain.Movie?) {
    movie?.let { setMovie(it) }
}
