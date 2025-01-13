package com.example.proyectofinalcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyectofinalcompose.navigation.AppNavigation
import com.example.proyectofinalcompose.ui.theme.ProyectoFinalComposeTheme
import com.example.proyectofinalcompose.viewmodel.LocalizationManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val languageCode = "es" // Puedes cambiar esto según tu preferencia
        LocalizationManager.loadStrings(this, languageCode)

        setContent {
            ProyectoFinalComposeTheme {
               AppNavigation()
            }

            /**
             * git add .
             * git commit -m "Descripción de los cambios realizados"
             * git push origin master
             *
             */
        }
    }
}

