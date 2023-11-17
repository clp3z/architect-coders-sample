package com.devexperto.architectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    navController: NavController = findNavController(),
    coroutineScope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    permissionRequester: PermissionRequester =
        PermissionRequester(
            this,
            ACCESS_COARSE_LOCATION
        )
) = MainState(navController, coroutineScope, permissionRequester)

class MainState(
    private val navController: NavController,
    private val coroutineScope: CoroutineScope,
    private val permissionRequester: PermissionRequester
) {

    fun onMovieClicked(movie: Movie) {
        val navigationAction = MainFragmentDirections.actionMainToDetail(movie)
        navController.navigate(navigationAction)
    }

    fun requestLocationPermission(onPermissionResult: (Boolean) -> Unit) {
        coroutineScope.launch {
            val isGranted = permissionRequester.request()
            onPermissionResult(isGranted)
        }
    }
}
