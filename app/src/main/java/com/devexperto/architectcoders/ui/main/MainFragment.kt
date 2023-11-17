package com.devexperto.architectcoders.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.launchAndCollect

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity() as AppCompatActivity))
    }

    private val moviesAdapter = MoviesAdapter {
        viewModel.onMovieClick(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = moviesAdapter
        }

        launchAndCollect(viewModel.viewState) { viewBinding.updateView(it) }
    }

    private fun FragmentMainBinding.updateView(viewState: MainViewModel.ViewState) {
        progressView.isVisible = viewState.isLoading
        moviesAdapter.submitList(viewState.movies)

        viewState.navigateToDetail?.let(::navigateToDetail)
    }

    private fun navigateToDetail(movie: Movie) {
        val navigationAction = MainFragmentDirections.actionMainToDetail(movie)
        findNavController().navigate(navigationAction)
        viewModel.onDetailNavigated()
    }
}
