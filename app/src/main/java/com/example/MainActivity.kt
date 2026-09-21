package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ui.screens.AntigravityMainScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        AntigravityMainScreen()
      }
    }
  }
}

@androidx.compose.runtime.Composable
fun Greeting(name: String) {
  androidx.compose.material3.Text(text = "Antigravity Agent: $name")
}

