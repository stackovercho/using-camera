package com.example.usingcamera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val permission: String = Manifest.permission.CAMERA
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissionGranted: Int = ContextCompat.checkSelfPermission(this, permission)
        if (permissionGranted == PackageManager.PERMISSION_GRANTED) {
            // use the camera
        } else {
            // ask for permission
            val permissionContract = ActivityResultContracts.RequestPermission()
            // val permissionCallback = PermissionResults()
            // permissionLauncher = registerForActivityResult(permissionContract, permissionCallback)
            permissionLauncher = registerForActivityResult(permissionContract) {
                if (it) Log.d("MainActivity", "permission granted!")
                else Log.d("MainActivity", "sorry, permission not granted")
            }
            permissionLauncher.launch(permission)
        }
    }

    inner class PermissionResults : ActivityResultCallback<Boolean> {
        override fun onActivityResult(result: Boolean?) {
            if (result != null && result == true) Log.d("MainActivity", "permission granted")
            else Log.d("MainActivity", "permission denied")
        }
    }
}