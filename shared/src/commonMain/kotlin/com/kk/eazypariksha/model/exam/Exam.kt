package com.kk.eazypariksha.model.exam

import kotlinx.datetime.LocalDateTime

data class Exam(
    val id: Int,
    val subject: Subject,
    val dateTime: LocalDateTime,
    val questions: List<Question> = emptyList()
)

data class Subject(
    val id: Int,
    val name: String,
    val code: String
)

data class Question(
    val id: Int,
    val title: String,
    val selectedOption: Int = Option.UNDEFINED,
    val options: List<Option> = emptyList()
)

data class Option(val label: String) {
    companion object {
        const val UNDEFINED = -1
    }
}