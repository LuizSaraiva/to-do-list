package com.example.bootcamptodolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class EntityTask (
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @PrimaryKey val id: Int = 0
    )