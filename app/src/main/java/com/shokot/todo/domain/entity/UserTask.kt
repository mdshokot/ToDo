package com.shokot.todo.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "user_task")
data class UserTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "task_id")
    val taskId: Int,

    val date: LocalDate = LocalDate.now(),

    val completed: Boolean = false
){
    val createdDateFormatted : String
        get() =date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}