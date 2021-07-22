package hr.trailovic.notesqkeeper

fun <T> MutableSet<T>.addOrRemove(element: T) {
    if (element in this) {
        this.remove(element)
    } else {
        this.add(element)
    }
}