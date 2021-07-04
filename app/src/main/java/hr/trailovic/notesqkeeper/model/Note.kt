package hr.trailovic.notesqkeeper.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @ColumnInfo var title: String?,
    @Embedded var body: NoteBody?,
    @PrimaryKey var id: Long = System.currentTimeMillis(),
)
