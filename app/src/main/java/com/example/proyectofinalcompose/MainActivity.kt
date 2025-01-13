package com.example.proyectofinalcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyectofinalcompose.navigation.AppNavigation
import com.example.proyectofinalcompose.ui.theme.ProyectoFinalComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProyectoFinalComposeTheme {
               AppNavigation()
            }
        }
    }
}

