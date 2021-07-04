package hr.trailovic.notesqkeeper.model

/**
 * Single Task (one unit of a list)
 * */
data class Task(
    var description: String = "",
    var isCompleted: Boolean = false,
)
