package com.example.proyectofinalcompose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuarioDao {


    @Query("SELECT * FROM Usuario")
    suspend fun getAllUsers(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE email = :email")
    suspend fun getUserByEmail(email: String): Usuario?

    @Insert
    suspend fun insertUser(user: Usuario)

    @Update
    suspend fun updateUser(user: Usuario)

    @Query("UPDATE Usuario SET accessCount = accessCount + 1 WHERE email = :email")
    suspend fun incrementAccessCount(email: String)

    @Query("UPDATE Usuario SET lastAccessDate = :lastAccessDate WHERE email = :email")
    suspend fun updateLastAccessDate(email: String, lastAccessDate: Long)

    @Query("SELECT * FROM Usuario WHERE name = :name")
    suspend fun getUserByName(name: String): Usuario?



}