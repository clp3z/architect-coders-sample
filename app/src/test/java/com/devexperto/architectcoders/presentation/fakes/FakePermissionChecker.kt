package com.devexperto.architectcoders.presentation.fakes

import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.PermissionChecker.Permission

class FakePermissionChecker : PermissionChecker {

    var isGranted = true

    override fun check(permission: Permission): Boolean = isGranted
}
