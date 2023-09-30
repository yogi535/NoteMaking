package com.example.notemaking

import android.content.Context
import androidx.room.Room
import com.example.notemaking.data.local.dao.TodoDao
import com.example.notemaking.data.local.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "notes.db")
            .fallbackToDestructiveMigration() // You can customize migration behavior here
            .build()
    }

    @Provides
    fun getTodoDao(noteDatabase: NoteDatabase): TodoDao {
        return noteDatabase.getNoteDao()

    }
}