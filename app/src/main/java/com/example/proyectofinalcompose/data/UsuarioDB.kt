package com.example.proyectofinalcompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class], version = 1)
abstract class UsuarioDB : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}

//Singleton para acceder a la base de datos
object DatabaseBuilder {

    @Volatile
    private var INSTANCE: UsuarioDB? = null

    fun getInstance(context: Context): UsuarioDB {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UsuarioDB::class.java,
                "app_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
