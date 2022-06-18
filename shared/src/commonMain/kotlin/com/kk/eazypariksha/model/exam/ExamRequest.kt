package com.kk.eazypariksha.model.exam

data class ExamRequest(
    val id: Int? = null,
    val subject: Subject,
    val timestamp: Long,
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