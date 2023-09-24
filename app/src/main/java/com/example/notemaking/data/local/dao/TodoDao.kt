package com.example.notemaking.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notemaking.data.local.models.Todo


@Dao
interface TodoDao {

    @Query("SELECT * FROM TodoListTable")
    fun getAllNotes(): List<Todo> // Use LiveData or Flow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Todo): Long

    @Update
    suspend fun updateNote(note: Todo): Int

    @Delete
    suspend fun deleteNote(note: Todo): Int
}



