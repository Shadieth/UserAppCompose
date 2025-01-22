package com.example.proyectofinalcompose.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object QueryApi : AppScreens("query_api")
    object ApiResponse : AppScreens("api_response")

}