package com.devexperto.architectcoders.model

import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import com.devexperto.architectcoders.ui.MainActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionChecker(activity: MainActivity, private val permission: String) {

    private var onRequest: (Boolean) -> Unit = {}
    private val launcher = activity.registerForActivityResult(RequestPermission()) { isGranted ->
        onRequest(isGranted)
    }

    /**
     * Uses the launcher to initiate a permission request. After the permission request returns
     * onRequest(isGranted) will be called.
     * All this is done in a coroutine that will be suspended until the request returns, and then
     * the actual lambda onRequest will be executed.
     */
    suspend fun request(): Boolean = suspendCancellableCoroutine { continuation ->
        onRequest = { isGranted ->
            continuation.resume(isGranted)
        }
        launcher.launch(permission)
    }
}
