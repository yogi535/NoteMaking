package com.example.notemaking.data.local.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notemaking.data.local.dao.TodoDao
import com.example.notemaking.data.local.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NoteRepository(private val todo: TodoDao) {

    private val _notesList = MutableLiveData<List<Todo>>()

    val notesList: LiveData<List<Todo>>
        get() = _notesList

    suspend fun getAllNotes() {
        return withContext(Dispatchers.IO) {
            _notesList.postValue(todo.getAllNotes())
        }
    }


    suspend fun insertNote(note: Todo) {
        todo.insertNote(note)
    }


    suspend fun updateNote(note: Todo) {
        todo.updateNote(note)
    }


    suspend fun deleteNote(note: Todo) {
        todo.deleteNote(note)
    }

}