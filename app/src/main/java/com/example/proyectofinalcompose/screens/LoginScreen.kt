package com.example.proyectofinalcompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalcompose.MainActivity
import com.example.proyectofinalcompose.data.UsuarioRepositorio
import com.example.proyectofinalcompose.navigation.AppScreens
import com.example.proyectofinalcompose.ui.theme.ProyectoFinalComposeTheme
import com.example.proyectofinalcompose.viewmodel.LocalizationManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Obtener el contexto y la actividad para detener el worker
    val activity = context as? MainActivity

    // Detener el worker cuando se navega a la segunda pantalla
    LaunchedEffect(Unit) {
        activity?.stopTimeWorker()
    }

    // Obtener instancia del repositorio
    val usuarioRepositorio = UsuarioRepositorio.getInstance(context)

    val loginTitle = LocalizationManager.getString("login_title")
    val userLabel = LocalizationManager.getString("user_label")
    val emailLabel = LocalizationManager.getString("email_label")
    val loginButton = LocalizationManager.getString("login_button")
    val noAccountText = LocalizationManager.getString("no_account_text")
    val errorEmail = LocalizationManager.getString("email_error")

    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailVisible by remember { mutableStateOf(false) }
    var usuarioIncorrecto by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }

    val isFormValid = usuario.isNotEmpty() && email.isNotEmpty() && isEmailValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = loginTitle,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de usuario
        TextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text(userLabel) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de email
        TextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = { Text(emailLabel) },
            visualTransformation = if (emailVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { emailVisible = !emailVisible }) {
                    Icon(
                        imageVector = if (emailVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        // Mensaje de error para email no válido
        if (!isEmailValid && email.isNotEmpty()) {
            Text(
                text = errorEmail,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de iniciar sesión
        Button(
            enabled = isFormValid,
            onClick = {
                scope.launch {
                    val existingUser = usuarioRepositorio.getUserByEmail(email)
                    if (existingUser == null) {
                        navController.navigate(AppScreens.RegisterScreen.route)
                    } else {
                        if (existingUser.name == usuario) {

                            val updatedUser = existingUser.copy(
                                accessCount = existingUser.accessCount + 1,
                                lastAccessDate = System.currentTimeMillis()
                            )
                            usuarioRepositorio.updateUser(updatedUser)
                            navController.navigate(AppScreens.QueryApi.route)
                        } else {
                            usuarioIncorrecto = true
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFormValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = loginButton)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de registro
        Text(
            text = noAccountText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clickable { navController.navigate(AppScreens.RegisterScreen.route) }
        )

        // Mensaje de error para usuario incorrecto
        if (usuarioIncorrecto) {
            Text(
                text = "Usuario incorrecto",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ProyectoFinalComposeTheme {
        LoginScreen(
            navController = rememberNavController(),
        )
    }
}
