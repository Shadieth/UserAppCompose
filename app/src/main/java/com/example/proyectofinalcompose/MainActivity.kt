package com.example.proyectofinalcompose

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyectofinalcompose.data.DatabaseBuilder
import com.example.proyectofinalcompose.navigation.AppNavigation
import com.example.proyectofinalcompose.ui.theme.ProyectoFinalComposeTheme
import com.example.proyectofinalcompose.viewmodel.LocalizationManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val handler = Handler(Looper.getMainLooper()) // Handler para actualizar la interfaz en el hilo principal
    private var isWorkerRunning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de la base de datos
        val database = DatabaseBuilder.getInstance(applicationContext)

        // Configuración del idioma de la aplicación
        setupLocalization()

        // Configuración de la UI
        setContent {
            ProyectoFinalComposeTheme {
                setupLocalization() // Configuración del idioma
                AppNavigation() // Configuración de la navegación principal
                startTimeWorker() // Inicio del worker para actualizar la interfaz)
            }
        }
    }

    // Iniciar el worker de la hora, independiente de las pantallas
     fun startTimeWorker() {
        if (!isWorkerRunning) {
            isWorkerRunning = true

            handler.post(object : Runnable {
                override fun run() {
                    //Actualizar la hora
                    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    showMessage("Son las $currentTime, puedes consultar la API")

                    // Repetir cada minuto
                    handler.postDelayed(this, 60 * 100) // 60 segundos en milisegundos
                }
            })

            }
        }

    // Función para detener el worker
    fun stopTimeWorker() {
        if (isWorkerRunning) {
            isWorkerRunning = false
            handler.removeCallbacksAndMessages(null) // Detener todas las tareas pendientes
        }
    }

    // Función para mostrar el mensaje (Toast)
    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimeWorker()
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
