package com.devexperto.architectcoders.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.data.toErrorMessage
import com.devexperto.architectcoders.databinding.FragmentDetailBinding
import com.devexperto.architectcoders.framework.datasources.MovieRetrofitDataSource
import com.devexperto.architectcoders.framework.datasources.MovieRoomDataSource
import com.devexperto.architectcoders.ui.common.app
import com.devexperto.architectcoders.ui.common.launchAndCollect
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val arguments: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        val application = requireActivity().app
        val localDataSource: MovieLocalDataSource = MovieRoomDataSource(application.movieDAO)
        val remoteDataSource: MovieRemoteDataSource = MovieRetrofitDataSource("df913d0e8d85eb724270797250eb400f")
        val regionRepository = RegionRepository(application)
        val moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
        DetailViewModelFactory(
            RequestMovieUseCase(moviesRepository),
            SwitchFavoriteUseCase(moviesRepository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentDetailBinding.bind(view)

        viewBinding.movieDetailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewBinding.favoriteActionButton.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        viewLifecycleOwner.launchAndCollect(viewModel.viewState) { viewState ->
            viewState.movie?.let {
                viewBinding.movie = it
                viewBinding.error = toErrorMessage(requireContext(), viewState.error)
            }
        }

        viewModel.onViewReady(arguments.movieId)
    }
}
