package com.kk.eazypariksha.data

import com.kk.eazypariksha.model.exam.*
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized

class InMemoryRepository : ExamRepository, SynchronizedObject() {
    private val examDrafts = mutableMapOf<Int, Exam>()
    private val scheduledExams = mutableMapOf<Int, Exam>()
    private val nextExamId = atomic(1)
    private val nextQuestionId = atomic(1)
    private val subjects = listOf(
        Subject(1, "Hindi", "HIN05"),
        Subject(2, "English", "ENG05"),
        Subject(3, "Science", "SCI05"),
        Subject(4, "Mathematics", "MAT05"),
        Subject(5, "Social Science", "SOC05"),
    )

    override suspend fun create(examRequest: ExamRequest) {
        check(examRequest.id == null) { "Exam id can't be non-null while creating" }
        val examId = nextExamId.getAndIncrement()
        val newExam = examRequest.copy(id = examId).toExam()
        synchronized(this) {
            scheduledExams[examId] = newExam
        }
    }

    override suspend fun saveDraft(examRequest: ExamRequest) {
        if (examRequest.id == null) {
            // Creating new draft
            val draftId = nextExamId.getAndIncrement()
            val draft = examRequest.copy(id = draftId).toExam()
            synchronized(this) {
                examDrafts[draftId] = draft
            }
        } else {
            synchronized(this) {
                examDrafts[examRequest.id] = examRequest.toExam()
            }
        }
    }

    override suspend fun getOngoing(): List<Exam> = scheduledExams.values.toList()

    override suspend fun getDrafts(): List<Exam> = examDrafts.values.toList()


    override suspend fun getAllSubjects(): List<Subject> = subjects

    private fun ExamRequest.toExam(): Exam {
        val examId = id ?: error("Tried creating exam with id=null")
        return Exam(
            id = examId,
            subject = subject,
            timestamp = timestamp,
            questions = questions.map { it.toQuestion(nextQuestionId.getAndIncrement()) },
        )
    }

    private fun QuestionRequest.toQuestion(id: Int) = Question(
        id = id,
        title = title,
        selectedOption = Option.UNDEFINED,
        options = options.map { it.toOption() }
    )

    private fun OptionRequest.toOption() = Option(label)

    companion object : SynchronizedObject() {
        private val instance = atomic<InMemoryRepository?>(null)
        fun getInstance(): ExamRepository {
            return instance.value ?: synchronized(this) {
                instance.value ?: InMemoryRepository().also {
                    instance.value = it
                }
            }
        }
    }
}