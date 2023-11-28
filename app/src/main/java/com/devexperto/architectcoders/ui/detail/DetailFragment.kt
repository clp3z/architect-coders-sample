package com.devexperto.architectcoders.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentDetailBinding
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.common.app
import com.devexperto.architectcoders.ui.common.launchAndCollect

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val arguments: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(MoviesRepository(requireActivity().app))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentDetailBinding.bind(view)

        viewBinding.movieDetailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewLifecycleOwner.launchAndCollect(viewModel.viewState) {
            viewBinding.movie = it.movie
        }

        viewModel.onViewReady(arguments.movieId)
    }
}
