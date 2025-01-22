package com.example.proyectofinalcompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.proyectofinalcompose.navigation.AppScreens

@Composable
fun QueryApi(navController: NavController) {

    Box (

    ) {
        Button(onClick = {
            navController.navigate(AppScreens.ApiResponse.route)
        }) {
            Text(text = "Consultar API")
        }
    }


}



