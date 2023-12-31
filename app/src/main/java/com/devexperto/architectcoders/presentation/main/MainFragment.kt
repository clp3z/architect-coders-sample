package com.devexperto.architectcoders.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.framework.toErrorMessage
import com.devexperto.architectcoders.presentation.common.app
import com.devexperto.architectcoders.presentation.common.launchAndCollect

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var component: MainFragmentComponent

    private val viewModel by viewModels<MainViewModel> {
        component.mainViewModelFactoryFactory
    }

    private lateinit var mainState: MainState

    private val moviesAdapter = MoviesAdapter {
        mainState.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.applicationComponent.plus(MainFragmentModule)
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
