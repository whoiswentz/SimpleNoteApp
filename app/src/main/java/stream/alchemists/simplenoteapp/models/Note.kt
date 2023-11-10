package stream.alchemists.simplenoteapp.models

import java.io.Serializable

data class Note(
    val title: String,
    val description: String
): Serializable { }