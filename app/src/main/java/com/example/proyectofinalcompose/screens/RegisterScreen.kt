package com.example.proyectofinalcompose.screens

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalcompose.data.Usuario
import com.example.proyectofinalcompose.data.UsuarioRepositorio
import com.example.proyectofinalcompose.ui.theme.ProyectoFinalComposeTheme
import com.example.proyectofinalcompose.viewmodel.LocalizationManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {



    val registerTitle = LocalizationManager.getString("register_title")
    val nameLabelRegister = LocalizationManager.getString("name_label_register")
    val emailLabelRegister = LocalizationManager.getString("email_label_register")
    val confirmEmailRegister = LocalizationManager.getString("confirm_email_register")
    val registerButton = LocalizationManager.getString("register_button")
    val validTextError = LocalizationManager.getString("valid_text_error")
    val emailsDontMatchError = LocalizationManager.getString("emails_dont_match_error")
    val completeAllError = LocalizationManager.getString("complete_all_error")
    val userExistsError = LocalizationManager.getString("user_exists_error")
    val emailExistsError = LocalizationManager.getString("email_exists_error")



    // Contenido de la pantalla de registro
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmEmail by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Usar un CoroutineScope para operaciones asíncronas
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Obtener instancia del repositorio
    val usuarioRepositorio = UsuarioRepositorio.getInstance(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = { Text(registerTitle)},
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
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
            isError = !isEmailValid // Aquí se controla el color del borde cuando hay un error
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
                        errorMessage = validTextError
                    }
                    email != confirmEmail -> {
                        errorMessage = emailsDontMatchError
                    }
                    email.isEmpty() || confirmEmail.isEmpty() || name.isEmpty() -> {
                        errorMessage = completeAllError
                    }
                    else -> {
                        scope.launch() {
                            // Verificar si ya existe un usuario con el mismo correo o nombre
                            val existingUserByEmail = usuarioRepositorio.getUserByEmail(email)
                            val existingUserByName = usuarioRepositorio.getUserByName(name)
                            if (existingUserByEmail != null) {
                                errorMessage =
                                return@launch
                            }
                            if (existingUserByName != null) {
                                errorMessage = "Ya existe un usuario con este nombre"
                                return@launch
                            }

                            // Guardar el usuario en la base de datos
                            val newUsuario = Usuario(
                                name = name,
                                email = email
                            )
                            //Agregar usuario a la base de datos
                            usuarioRepositorio.addUser(newUsuario)

                            // Navegar a la siguiente pantalla después de registrar al usuario
                            navController.navigate("query_api")
                        }
                        errorMessage = "" // Limpiar cualquier mensaje previo
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
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
        RegisterScreen(
            navController = rememberNavController(),
        )
    }
}

