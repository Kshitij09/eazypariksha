package com.kk.eazypariksha.model.exam

import kotlinx.datetime.LocalDateTime

data class ExamRequest(
    val id: Int? = null,
    val subject: Subject? = null,
    val dateTime: LocalDateTime? = null,
    val questions: List<QuestionRequest> = emptyList()
)

data class SubjectRequest(
    val name: String,
    val code: String
)

data class QuestionRequest(
    val title: String,
    val options: List<OptionRequest> = emptyList()
)

data class OptionRequest(val label: String)