package com.devexperto.architectcoders.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.framework.AndroidPermissionChecker
import com.devexperto.architectcoders.framework.PlayServicesLocationDataSource
import com.devexperto.architectcoders.framework.database.MovieRoomDataSource
import com.devexperto.architectcoders.framework.retrofit.MovieRetrofitDataSource
import com.devexperto.architectcoders.framework.toErrorMessage
import com.devexperto.architectcoders.ui.common.app
import com.devexperto.architectcoders.ui.common.launchAndCollect
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        val application = requireActivity().app
        val localDataSource: MovieLocalDataSource = MovieRoomDataSource(application.movieDAO)
        val remoteDataSource: MovieRemoteDataSource = MovieRetrofitDataSource("df913d0e8d85eb724270797250eb400f")
        val regionRepository = RegionRepository(
            PlayServicesLocationDataSource(application),
            AndroidPermissionChecker(application)
        )
        val moviesRepository = MoviesRepository(
            regionRepository,
            localDataSource,
            remoteDataSource
        )
        MainViewModelFactory(
            GetPopularMoviesUseCase(moviesRepository),
            RequestPopularMoviesUseCase(moviesRepository)
        )
    }

    private lateinit var mainState: MainState

    private val moviesAdapter = MoviesAdapter {
        mainState.onMovieClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val viewBinding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = moviesAdapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.viewState) {
            viewBinding.isLoading = it.isLoading
            viewBinding.movies = it.movies
            viewBinding.error = toErrorMessage(it.error)
        }

        mainState.requestLocationPermission {
            viewModel.onViewReady()
        }
    }
}
