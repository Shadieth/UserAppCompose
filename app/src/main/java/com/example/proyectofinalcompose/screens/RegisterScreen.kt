package com.example.proyectofinalcompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalcompose.ui.theme.ProyectoFinalComposeTheme
import com.example.proyectofinalcompose.viewmodel.LocalizationManager

@Composable
fun RegisterScreen(navController: NavController) {
    val registerTitle = LocalizationManager.getString("register_title")
    val nameLabelRegister = LocalizationManager.getString("name_label_register")
    val emailLabelRegister = LocalizationManager.getString("email_label_register")
    val confirmEmailRegister = LocalizationManager.getString("confirm_email_register")
    val registerButton = LocalizationManager.getString("register_button")

    // Contenido de la pantalla de registro
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmEmail by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = registerTitle,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Nombre
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(nameLabelRegister) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Correo electrónico
        TextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = isValidEmail(it)
            },
            label = { Text(emailLabelRegister) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            isError = !isEmailValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirmar correo electrónico
        TextField(
            value = confirmEmail,
            onValueChange = { confirmEmail = it },
            label = { Text(confirmEmailRegister) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de registro
        Button(
            onClick = {
                when {
                    !isValidEmail(email) -> {
                        errorMessage = "Por favor, introduce un correo válido."
                    }
                    email != confirmEmail -> {
                        errorMessage = "Los correos electrónicos no coinciden."
                    }
                    email.isEmpty() || confirmEmail.isEmpty() || name.isEmpty() -> {
                        errorMessage = "Por favor, completa todos los campos."
                    }
                    else -> {
                        navController.navigate("query_api")
                        errorMessage = "" // Limpiar cualquier mensaje previo
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = registerButton)
        }

        // Mensaje de error
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ProyectoFinalComposeTheme {
        RegisterScreen(rememberNavController())
    }
}
