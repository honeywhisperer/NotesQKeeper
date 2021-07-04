package hr.trailovic.notesqkeeper.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="body") var body: String,
    @PrimaryKey var id: Long = System.currentTimeMillis(),
)