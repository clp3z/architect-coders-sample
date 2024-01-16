package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.testShared.sampleMovie
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SwitchFavoriteUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val moviesRepository = mock<MoviesRepository>()
        val useCase = SwitchFavoriteUseCase(moviesRepository)

        useCase.invoke(sampleMovie)

        verify(moviesRepository).switchFavorite(sampleMovie)
    }
}
