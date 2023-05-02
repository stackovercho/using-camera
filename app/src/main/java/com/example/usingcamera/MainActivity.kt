package com.example.usingcamera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val permission: String = Manifest.permission.CAMERA
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  cameraContract = ActivityResultContracts.TakePicturePreview()
        val permissionGranted: Int = ContextCompat.checkSelfPermission(this, permission)

        val cameraCallback = CameraResults()
        cameraLauncher = registerForActivityResult(cameraContract, cameraCallback)

        if (permissionGranted == PackageManager.PERMISSION_GRANTED) {
            // use the camera
        } else {
            // ask for permission
            val permissionContract = ActivityResultContracts.RequestPermission()
            val permissionCallback = PermissionResults()
            permissionLauncher = registerForActivityResult(permissionContract, permissionCallback)
            // permissionLauncher = registerForActivityResult(permissionContract) {
            //     if (it) Log.d("MainActivity", "permission granted!")
            //     else Log.d("MainActivity", "sorry, permission not granted")
            // }
            permissionLauncher.launch(permission)
        }
    }

    inner class PermissionResults : ActivityResultCallback<Boolean> {
        override fun onActivityResult(result: Boolean?) {
            if (result != null && result == true) {
                // use the camera
                cameraLauncher.launch(null)
                Log.d("MainActivity", "permission granted") }
            else Log.d("MainActivity", "permission denied")
        }
    }

    inner class CameraResults : ActivityResultCallback<Bitmap?> {
        override fun onActivityResult(result: Bitmap?) {
            if (result != null) {
                // display it inside image
                val imageView: ImageView = findViewById(R.id.image)
                imageView.setImageBitmap(result)
            }
            else Log.d("MainActivity", "permission denied")
        }
    }
}