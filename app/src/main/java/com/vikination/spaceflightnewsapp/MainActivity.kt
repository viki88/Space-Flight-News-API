package com.vikination.spaceflightnewsapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.auth0.android.Auth0
import com.vikination.spaceflightnewsapp.ui.theme.MainNavHost
import com.vikination.spaceflightnewsapp.ui.theme.SpaceFlightNewsAppTheme
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import com.vikination.spaceflightnewsapp.ui.utils.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var authManager: AuthManager
    @Inject lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!permissionManager.hasNotificationPermission())
            permissionManager.requestNotificationPermission(this)
        val isFromLogout = intent.getBooleanExtra("FROM_LOGOUT", false)
        if (isFromLogout) {
            authManager.logoutInBackground(this, onSuccess = {
                finish()
            }, onError = {
                finish()
            })
        }
        enableEdgeToEdge()
        setContent {
            SpaceFlightNewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavHost(
                        authManager,
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == PermissionManager.REQUEST_CODE_NOTIFICATIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpaceFlightNewsAppTheme {
        Greeting("Android")
    }
}