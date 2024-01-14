package com.devexperto.architectcoders.data

import arrow.core.right
import com.devexperto.architectcoders.data.RegionRepository.Companion.DEFAULT_REGION
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private val sampleMovie = Movie(
    id = 1,
    title = "title",
    overview = "overview",
    releaseDate = "releaseDate",
    posterUrl = "posterUrl",
    originalTitle = "originalTitle",
    originalLanguage = "originalLanguage",
    popularity = 1.0,
    voteAverage = 1.0,
    isFavorite = true
)

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var regionRepository: RegionRepository

    @Mock
    lateinit var localDataSource: MovieLocalDataSource

    @Mock
    lateinit var remoteDataSource: MovieRemoteDataSource

    private lateinit var moviesRepository: MoviesRepository

    private val localMovies = flowOf(listOf(sampleMovie))

    @Before
    fun setUp() {
        whenever(localDataSource.movies).thenReturn(localMovies)
        moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `popularMovies returns movies from local data source if available`(): Unit = runBlocking {
        // When
        val result = moviesRepository.popularMovies

        assertEquals(localMovies, result)
    }

    @Test
    fun `popularMovies are saved to local data source when it's empty`(): Unit = runBlocking { // Given
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.getLastRegion()).thenReturn(DEFAULT_REGION)

        val remoteMovies = listOf(sampleMovie)
        whenever(remoteDataSource.getPopularMovies(any())).thenReturn(remoteMovies.right())

        moviesRepository.requestPopularMovies()

        verify(localDataSource).save(remoteMovies)
    }

    @Test
    fun `Requesting a movie by id are retrieved from the local data source`(): Unit = runBlocking {
        val id = 7
        val movie = flowOf(sampleMovie.copy(id = id))
        whenever(localDataSource.get(id)).thenReturn(movie)

        val result = moviesRepository.requestMovieById(id)

        assertEquals(movie, result)
    }

    @Test
    fun `switchFavorite marks as favorite if not favorite`(): Unit = runBlocking {
        val movie = sampleMovie.copy(isFavorite = false)

        moviesRepository.switchFavorite(movie)

        verify(localDataSource).update(argThat { isFavorite })
    }

    @Test
    fun `switchFavorite un-marks as favorite if favorite`(): Unit = runBlocking {
        val movie = sampleMovie.copy(isFavorite = true)

        moviesRepository.switchFavorite(movie)

        verify(localDataSource).update(argThat { !isFavorite })
    }
}
