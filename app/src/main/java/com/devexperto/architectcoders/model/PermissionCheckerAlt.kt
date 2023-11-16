package com.devexperto.architectcoders.model

import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import com.devexperto.architectcoders.ui.main.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PermissionCheckerAlt(
    private val activity: MainFragment,
    private val permission: String,
    private val listener: PermissionsListener
) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun request() {
        coroutineScope.launch {
            activity.registerForActivityResult(RequestPermission()) { isGranted ->
                listener.onPermissionResult(isGranted)
            }.launch(permission)
        }
    }
}

interface PermissionsListener {
    fun onPermissionResult(isGranted: Boolean)
}
