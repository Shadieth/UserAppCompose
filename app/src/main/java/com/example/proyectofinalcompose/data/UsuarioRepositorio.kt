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

   suspend fun getUserByName(name: String): Usuario? {
        return usuarioDao.getUserByName(name)
   }
}
