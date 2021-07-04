package hr.trailovic.notesqkeeper.model

import androidx.room.Entity

/**
 * Textual content (one option for Note Body)
 * */
@Entity
data class NoteDescription(
    var description: String
): NoteBody() {
    override fun toSimpleString(): String {
        return description
    }

    companion object{
        fun fromSimpleString(input: String): NoteDescription{
            return NoteDescription(input)
        }
    }
}
