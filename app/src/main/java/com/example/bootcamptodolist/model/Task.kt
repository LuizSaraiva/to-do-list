package com.example.bootcamptodolist.model

import android.text.BoringLayout
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "resume") val resume:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "status") var status: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)