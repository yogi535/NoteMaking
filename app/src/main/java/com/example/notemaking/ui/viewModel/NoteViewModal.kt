package com.example.notemaking.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notemaking.data.local.models.Todo
import com.example.notemaking.data.local.repository.NoteRepository


class NoteViewModal (private val noteRepository: NoteRepository) : ViewModel() {

    val notesList: LiveData<List<Todo>>
        get() = noteRepository.notesList

    suspend fun getAllNotes() {
        noteRepository.getAllNotes()
    }

    suspend fun insertNote(note: Todo) {
       noteRepository.insertNote(note)
    }

    suspend fun updateNote(note: Todo) {
       noteRepository.updateNote(note)
    }

    suspend fun deleteNote(note: Todo) {
        noteRepository.deleteNote(note)
    }
}

