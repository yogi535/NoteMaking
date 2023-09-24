package com.example.notemaking.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "TodoListTable")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "content") val content: String? = null
) {
    constructor() : this(0, "", "")
}




