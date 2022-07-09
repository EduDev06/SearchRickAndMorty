package com.example.searchapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.searchapp.ui.home.HomeScreen
import com.example.searchapp.ui.theme.SearchAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchAppTheme {
                val context = LocalContext.current
                HomeScreen(context = context)
            }
        }
    }
}