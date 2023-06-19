package com.example.newtest

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonDao {
    @Insert
    suspend fun insert(person: Person)

    @Query("SELECT * FROM persons")
    suspend fun getAll(): List<Person>

    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)
}