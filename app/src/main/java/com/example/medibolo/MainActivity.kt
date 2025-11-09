package com.example.medibolo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.medibolo.ui.camera.CameraPermissionScreen
import com.example.medibolo.ui.theme.MediBoloTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediBoloTheme {
                LiveScanScreen()
            }
        }
    }
}

@Composable
fun LiveScanScreen() {
    var scannedText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPermissionScreen(
            onTextScanned = { result ->
                scannedText = result
            }
        )

        if (scannedText.isNotBlank()) {
            Text(
                text = scannedText,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
