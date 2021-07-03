package hr.trailovic.notesqkeeper.model

import androidx.room.Entity

@Entity
data class Note(
    var title: String?,
    var body: NoteBody?,
)
