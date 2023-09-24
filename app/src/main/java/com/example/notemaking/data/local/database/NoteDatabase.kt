package com.example.notemaking.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notemaking.data.local.dao.TodoDao
import com.example.notemaking.data.local.models.Todo

@Database(
    entities = [Todo::class],
    version = 2, // Updated version to 2
    exportSchema = true
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): TodoDao

    companion object {
        private const val DB_NAME = "notes.db"

        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        // Define a migration from version 1 to version 2
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // You should write the necessary SQL statements here to upgrade your schema.
                // For example, if you added a new table or columns, create tables, alter tables, etc.
                // database.execSQL("ALTER TABLE ...")
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DB_NAME
        )
            .addMigrations(MIGRATION_1_2) // Add the migration here
            .build()

        fun close() {
            instance?.close()
            instance = null
        }
    }
}


