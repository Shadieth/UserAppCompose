package com.example.proyectofinalcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyectofinalcompose.data.DatabaseBuilder
import com.example.proyectofinalcompose.navigation.AppNavigation
import com.example.proyectofinalcompose.ui.theme.ProyectoFinalComposeTheme
import com.example.proyectofinalcompose.viewmodel.LocalizationManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de la base de datos
        val database = DatabaseBuilder.getInstance(applicationContext)

        // Configuración del idioma de la aplicación
        setupLocalization()

        // Configuración de la UI
        setContent {
            ProyectoFinalComposeTheme {
                AppNavigation() // Configuración de la navegación principal
            }
        }
    }

    /**
     * Configura la localización de la aplicación.
     * Puedes modificar el idioma según las preferencias del usuario o del sistema.
     */
    private fun setupLocalization() {
        // Aquí puedes configurar dinámicamente el idioma (por ejemplo, obteniéndolo de SharedPreferences).
        val languageCode = "es" // Cambia esto según sea necesario (ej., "en", "fr", etc.).
        LocalizationManager.loadStrings(this, languageCode)
    }
}


/**
 * git add .
 * git commit -m "Descripción de los cambios realizados"
 * git push origin master
 *
 */
