package com.example.proyectofinalcompose.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalcompose.screens.ApiResponse
import com.example.proyectofinalcompose.screens.LoginScreen
import com.example.proyectofinalcompose.screens.QueryApi
import com.example.proyectofinalcompose.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(AppScreens.QueryApi.route) {
            QueryApi(navController)
        }
        composable(AppScreens.ApiResponse.route) {
            ApiResponse(navController)
        }

    }
}