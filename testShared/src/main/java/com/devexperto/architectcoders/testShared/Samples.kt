package com.devexperto.architectcoders.testShared

import com.devexperto.architectcoders.domain.Movie

val sampleMovie = Movie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "Title",
    "EN",
    5.0,
    5.1,
    false
)

val sampleMovieList = listOf(
    sampleMovie.copy(1),
    sampleMovie.copy(2),
    sampleMovie.copy(3),
    sampleMovie.copy(4),
    sampleMovie.copy(5),
)