package com.example.playlistmaker.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.playlistmaker.ui.navigation.PlaylistHost
import com.example.playlistmaker.ui.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel // Импорт Koin

class MainActivity : ComponentActivity() {

    // Koin автоматически создаст ViewModel
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlaylistHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavController(),
                        searchViewModel = searchViewModel
                    )
                }
            }
        }
    }
}