package com.devexperto.architectcoders.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RemoteResult(
    val page: Int,
    val results: List<RemoteMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

@Parcelize
data class RemoteMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val adult: Boolean,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    val popularity: Double,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("vote_count") val voteCount: Int,
    val video: Boolean,
) : Parcelable {

    val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w780${backdropPath ?: posterPath}"
}
