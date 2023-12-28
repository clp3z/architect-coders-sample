package com.devexperto.architectcoders.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.framework.toErrorMessage
import com.devexperto.architectcoders.presentation.common.launchAndCollect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModel()

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
