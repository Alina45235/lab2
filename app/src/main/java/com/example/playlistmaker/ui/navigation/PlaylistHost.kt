package com.example.playlistmaker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlistmaker.ui.main.MainScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.setting.SettingsScreen
import com.example.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun PlaylistHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
        modifier = modifier
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) }
            )
        }
        composable(Screen.SEARCH.route) {
            SearchScreen(
                searchViewModel = searchViewModel,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}