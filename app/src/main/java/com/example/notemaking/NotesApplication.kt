package com.example.notemaking

import android.app.Application
import com.example.notemaking.data.local.database.NoteDatabase


class NotesApplication : Application() {

    lateinit var noteDatabase: NoteDatabase

    override fun onCreate() {
        super.onCreate()
        noteDatabase = NoteDatabase.invoke(this)
    }
}





