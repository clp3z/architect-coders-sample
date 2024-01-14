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
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RequestMovieUseCaseTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    @Test
    fun `Invoke returns requested movie by id`(): Unit = runBlocking {
        val id = 7
        val movie = flowOf(mock<Movie>().copy(id = id))
        whenever(moviesRepository.requestMovieById(id)).thenReturn(movie)

        val useCase = RequestMovieUseCase(moviesRepository)

        val result = useCase.invoke(id)

        assertEquals(movie, result)
    }
}
