package com.dscreate_app.weatherdata.utils

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun AppCompatActivity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(text: String) {
    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.isPermissionGranted(perm: String): Boolean {
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity, perm) == PackageManager.PERMISSION_GRANTED
}