package com.example.notemaking.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notemaking.data.local.dao.TodoDao
import com.example.notemaking.data.local.models.Todo

@Database(entities = [Todo::class], version = 2, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): TodoDao
}


