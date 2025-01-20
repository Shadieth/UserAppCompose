package com.example.proyectofinalcompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "Usuario",
    indices = [
        Index(value = ["email"], unique = true), // Índice único para email
        Index(value = ["name"], unique = true)  // Índice único para name
    ]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val accessCount: Int = 0,
    val lastAccessDate: Long? = null // Almacena la fecha como timestamp
)

