package com.example.proyectofinalcompose.data

import android.content.Context

class UsuarioRepositorio private constructor(private val usuarioDao: UsuarioDao) {

    companion object {
        @Volatile
        private var instance: UsuarioRepositorio? = null

        // Método para obtener la instancia del repositorio
        fun getInstance(context: Context): UsuarioRepositorio {
            return instance ?: synchronized(this) {
                val database = DatabaseBuilder.getInstance(context)
                val newInstance = UsuarioRepositorio(database.usuarioDao())
                instance = newInstance
                newInstance
            }
        }
    }

    // Métodos del repositorio para interactuar con el DAO

    // Obtener todos los usuarios
    suspend fun getAllUsers(): List<Usuario> {
        return usuarioDao.getAllUsers()
    }

    // Obtener un usuario por su email
    suspend fun getUserByEmail(email: String): Usuario? {
        return usuarioDao.getUserByEmail(email)
    }

    // Insertar un usuario en la base de datos
    suspend fun addUser(usuario: Usuario) {
        usuarioDao.insertUser(usuario)
    }

    suspend fun updateUser(usuario: Usuario) {
        usuarioDao.updateUser(usuario)
    }

    // Incrementar el contador de accesos de un usuario
    suspend fun incrementAccessCount(email: String) {
        usuarioDao.incrementAccessCount(email)
    }

    // Actualizar la fecha del último acceso de un usuario
    suspend fun updateLastAccessDate(email: String, lastAccessDate: Long) {
        usuarioDao.updateLastAccessDate(email, lastAccessDate)
    }
}
