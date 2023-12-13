package com.devexperto.architectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    context: Context = requireContext(),
    navController: NavController = findNavController(),
    coroutineScope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    permissionRequester: PermissionRequester =
        PermissionRequester(
            this,
            ACCESS_COARSE_LOCATION
        )
) = MainState(context, navController, coroutineScope, permissionRequester)

class MainState(
    private val context: Context,
    private val navController: NavController,
    private val coroutineScope: CoroutineScope,
    private val permissionRequester: PermissionRequester
) {

    fun onMovieClicked(movie: Movie) {
        val navigationAction = MainFragmentDirections.actionMainToDetail(movie.id)
        navController.navigate(navigationAction)
    }

    fun requestLocationPermission(onPermissionResult: (Boolean) -> Unit) {
        coroutineScope.launch {
            val isGranted = permissionRequester.request()
            onPermissionResult(isGranted)
        }
    }

    fun toErrorMessage(error: Error?): String = error?.let {
        when (it) {
            is Error.Sever -> context.getString(R.string.sever_error_code, it.code)
            is Error.Unknown -> context.getString(R.string.unknown_error, it.message)
            else -> context.getString(R.string.connectivity_error)
        }
    } ?: ""
}
