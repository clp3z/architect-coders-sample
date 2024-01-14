package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class RequestPopularMoviesUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val moviesRepository = mock<MoviesRepository>()
        val useCase = RequestPopularMoviesUseCase(moviesRepository)

        useCase()

        verify(moviesRepository).requestPopularMovies()
    }
}
