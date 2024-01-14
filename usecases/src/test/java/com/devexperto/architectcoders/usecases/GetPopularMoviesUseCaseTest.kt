package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseTest {

    @Mock lateinit var moviesRepository: MoviesRepository

    @Test
    fun `Invoke returns popular movies`(): Unit = runBlocking {
        val movie = mock<Movie>()
        val movies = flowOf(listOf(movie))
        whenever(moviesRepository.popularMovies).thenReturn(movies)

        val useCase = GetPopularMoviesUseCase(moviesRepository)

        val result = useCase.invoke()

        assertEquals(movies, result)
    }

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val moviesRepository = mock<MoviesRepository>()
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)

        requestPopularMoviesUseCase()

        verify(moviesRepository).requestPopularMovies()
    }
}
