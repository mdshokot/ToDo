package com.shokot.todo.domain.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Entity(
    tableName = "graph",
    indices = [Index(value = ["user_id", "task_id", "date"], unique = true)]
)
data class Graph(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "task_id")
    val taskId: Int,

    val valore: Int? = null,

    val date: LocalDate = LocalDate.now()
){
    val createdDateFormatted : String
        get() =date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}
