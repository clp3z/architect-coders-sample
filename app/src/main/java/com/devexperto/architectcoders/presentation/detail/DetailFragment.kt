package com.devexperto.architectcoders.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentDetailBinding
import com.devexperto.architectcoders.framework.toErrorMessage
import com.devexperto.architectcoders.presentation.common.app
import com.devexperto.architectcoders.presentation.common.launchAndCollect

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var component: DetailFragmentComponent

    private val arguments: DetailFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailViewModel> {
        component.detailViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.applicationComponent.plus(DetailFragmentModule)
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
                viewBinding.error = toErrorMessage(viewState.error)
            }
        }

        viewModel.onViewReady(arguments.movieId)
    }
}
