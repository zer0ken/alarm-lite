package com.example.alarmapp

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.ui.theme.AlarmAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setContent(intent)
    }

    @OptIn(ExperimentalPermissionsApi::class)
    private fun setContent(intent: Intent? = null) {
        setContent {
            AlarmAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    intent
                        ?.let { navController.handleDeepLink(it) }
                        ?: MainNaviGraph(navController = navController)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val postNotificationPermission =
                            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

                        LaunchedEffect(true) {
                            if (!postNotificationPermission.status.isGranted) {
                                postNotificationPermission.launchPermissionRequest()
                            }
                        }
                    }
                }
            }
        }
    }
}