package com.example.proyectofinalcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalcompose.MainActivity
import com.example.proyectofinalcompose.navigation.AppScreens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QueryApi(navController: NavController) {

    val currentDate = getCurrentDate()

    // Obtener el contexto y la actividad para controlar el worker
    val context = LocalContext.current
    val activity = context as? MainActivity

    // Iniciar el worker cuando se entra en la pantalla principal
    LaunchedEffect(Unit) {
        activity?.startTimeWorker()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Fondo del color del tema actual
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp, // Altura de la sombra
                    shape = RoundedCornerShape(16.dp), // Esquinas redondeadas
                    clip = false
                )
                .background(MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(16.dp)) // Fondo del botón
                .size(200.dp) // Tamaño cuadrado
        ) {
            Button(
                onClick = {
                    navController.navigate(AppScreens.ApiResponse.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Botón sin color, usa el fondo definido en el Box
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.fillMaxSize() // El botón ocupa todo el Box
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = "Consultar API",
                    color = MaterialTheme.colorScheme.onTertiary // Color de texto adecuado para el color terciario

                )
            }
        }
    }
}

// Función para obtener la fecha actual
fun getCurrentDate(): String {
    val format = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
    return format.format(Date())
}

@Preview(showBackground = true)
@Composable
fun QueryApiPreview() {
    QueryApi(navController = rememberNavController())
}



