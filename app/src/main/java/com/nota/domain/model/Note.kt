package com.nota.domain.model

import java.io.Serializable

data class Note(
    val noteId: Long,
    val noteTitle: String,
    val noteText: String
): Serializable
