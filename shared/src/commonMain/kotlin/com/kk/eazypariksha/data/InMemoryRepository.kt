package com.kk.eazypariksha.data

import com.kk.eazypariksha.model.FakeData
import com.kk.eazypariksha.model.exam.*
import io.github.aakira.napier.Napier
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object InMemoryRepository : ExamRepository {
    private val examDrafts = mutableMapOf<Int, ExamRequest>()
    private val scheduledExams = mutableMapOf<Int, Exam>()
    private var nextExamId = 1
    private var nextQuestionId = 1
    private val subjects = FakeData.subjects

    override suspend fun create(examRequest: ExamRequest) {
        check(examRequest.id == null) { "Exam id can't be non-null while creating" }
        val examId = nextExamId++
        val newExam = examRequest.copy(id = examId).toExam()
        scheduledExams[examId] = newExam
    }

    override suspend fun saveDraft(examRequest: ExamRequest) {
        if (examRequest.id == null) {
            // Creating new draft
            val draftId = nextExamId++
            val draft = examRequest.copy(id = draftId)
            examDrafts[draftId] = draft
        } else {
            examDrafts[examRequest.id] = examRequest
        }
        Napier.d("Currently ${examDrafts.size} drafts are in memory")
    }

    override suspend fun getOngoing(): List<Exam> = scheduledExams.values.toList()

    override suspend fun getDrafts(): List<ExamRequest> = examDrafts.values.toList()


    override suspend fun getAllSubjects(): List<Subject> = subjects

    private fun ExamRequest.toExam(): Exam {
        id ?: error("exam id can't be null")
        subject ?: error("exam subject can't be null")
        dateTime ?: error("exam date & time can't be null")
        return Exam(
            id = id,
            subject = subject,
            dateTime = dateTime,
            questions = questions.map { it.toQuestion(nextQuestionId++) },
        )
    }

    private fun QuestionRequest.toQuestion(id: Int) = Question(
        id = id,
        title = title,
        selectedOption = Option.UNDEFINED,
        options = options.map { it.toOption() }
    )

    private fun OptionRequest.toOption() = Option(label)

}