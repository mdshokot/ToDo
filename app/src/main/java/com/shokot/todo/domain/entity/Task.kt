package com.shokot.todo.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "task", indices = [Index(value = ["user_id", "graph_name"], unique = true)])
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int  = 0,
    @ColumnInfo(name="user_id")
    val userId : Int,
    val title : String="",
    val description : String = "",
    @ColumnInfo(name="graph_name")
    val graphName : String,
    val type : String,
    val value : Int? = null,
    val favorite : Boolean = false
    )