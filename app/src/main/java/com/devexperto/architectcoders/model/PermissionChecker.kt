package com.devexperto.architectcoders.model

import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionChecker(activity: AppCompatActivity, private val permission: String) {

    private var onRequest: (Boolean) -> Unit = {}
    private val launcher = activity.registerForActivityResult(RequestPermission()) { isGranted ->
        onRequest(isGranted)
    }

    /**
     * Uses the [launcher] to initiate a permission verification request.
     * After the verification result returns, onRequest(isGranted) callback will be called.
     * All of this is done in a coroutine that will be suspended until the registerForActivityResult
     * result returns, and then the rewritten lambda onRequest will be executed.
     */
    suspend fun request(): Boolean = suspendCancellableCoroutine { continuation ->
        onRequest = { isGranted ->
            continuation.resume(isGranted)
        }
        launcher.launch(permission)
    }
}
