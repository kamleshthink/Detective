package com.example.detectiveapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent // Import for Jetpack Compose
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.detectiveapp.ui.theme.DetectiveAppTheme

class MainActivity : ComponentActivity() {

    // Permissions array to access gallery, contacts, SMS, and camera
    private val requiredPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.CAMERA
    )

    // Request permissions when needed
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allPermissionsGranted = permissions.values.all { it }
            if (allPermissionsGranted) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if permissions are granted
        if (!hasPermissions()) {
            requestPermissionLauncher.launch(requiredPermissions)
        }

        setContent { // This is where composables are invoked inside the activity
            DetectiveAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    // Function to check if all permissions are granted
    private fun hasPermissions(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
