package com.devexperto.architectcoders.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.common.launchAndCollect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().application))
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

        with(viewModel.viewState) {
            diff(
                mapping = { it.isLoading },
                action = { viewBinding.progressView.isVisible = it }
            )
            diff(
                mapping = { it.movies },
                action = moviesAdapter::submitList
            )
        }

        mainState.requestLocationPermission {
            viewModel.onViewReady()
        }
    }

    private fun <T, U : Any> Flow<T>.diff(mapping: (T) -> U, action: (U) -> Unit) {
        viewLifecycleOwner.launchAndCollect(
            flow = map(mapping).distinctUntilChanged(),
            action = action
        )
    }
}
