package com.kk.eazypariksha.stateholder.addexam

import com.kk.eazypariksha.data.ExamRepository
import com.kk.eazypariksha.model.exam.ExamRequest
import com.kk.eazypariksha.model.exam.QuestionRequest
import com.kk.eazypariksha.model.exam.Subject
import com.kk.eazypariksha.stateholder.BaseContainerHost
import com.kk.eazypariksha.ui.StringConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.LocalDateTime
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

data class AddExamState(
    val subjects: List<Subject> = emptyList(),
    val dateTime: LocalDateTime? = null,
    val examId: Int? = null,
    val currentQuestion: QuestionRequest? = null,
    val questions: List<QuestionRequest> = emptyList(),
    val selectedSubjectIndex: Int = 0,
    val isLoading: Boolean = false,
) {
    val selectedSubject: Subject? = subjects.getOrNull(selectedSubjectIndex)
}

abstract class AddExamStateHolder(
    coroutineScope: CoroutineScope
) : BaseContainerHost<AddExamState, AddExamEffects>(coroutineScope) {
    open fun setSelectedSubject(index: Int) {}
    open fun setDateTime(dateTime: LocalDateTime) {}
    open fun saveDraft() {}
    open fun submitExam() {}
    open fun saveQuestion() {}
}

interface FeedbackEffect {
    val feedback: String
}

sealed interface AddExamEffects {
    data class SubjectNotSet(override val feedback: String) : AddExamEffects, FeedbackEffect
    data class DateTimeNotSet(override val feedback: String) : AddExamEffects, FeedbackEffect
    data class DraftSaved(override val feedback: String) : AddExamEffects, FeedbackEffect
}

internal class RealAddExamStateHolder(
    coroutineScope: CoroutineScope,
    private val repository: ExamRepository,
) : AddExamStateHolder(coroutineScope) {
    override val initialState = AddExamState()

    override fun onCreate(initialState: AddExamState) {
        super.onCreate(initialState)
        loadSubjects()
    }

    private fun loadSubjects() = intent {
        setLoading(true)
        val subjects = repository.getAllSubjects()
        reduce { state.copy(subjects = subjects) }
        setLoading(false)
    }

    override fun setSelectedSubject(index: Int) = intent {
        reduce { state.copy(selectedSubjectIndex = index) }
    }

    override fun setDateTime(dateTime: LocalDateTime) = intent {
        reduce { state.copy(dateTime = dateTime) }
    }

    override fun saveDraft() = intent {
        /*val selectedSubject = state.selectedSubject
        if (selectedSubject == null) {
            postSideEffect(AddExamEffects.SubjectNotSet(StringConstant.subjectNotSelected))
            return@intent
        }
        val dateTime = state.dateTime
        if (dateTime == null) {
            postSideEffect(AddExamEffects.DateTimeNotSet(StringConstant.dateTimeNotSet))
            return@intent
        }*/
        val request = ExamRequest(
            state.examId,
            state.selectedSubject,
            state.dateTime,
            state.questions
        )
        repository.saveDraft(request)
        postSideEffect(AddExamEffects.DraftSaved(StringConstant.draftSaved))
    }

    override fun saveQuestion() = intent {

    }

    private suspend fun SimpleSyntax<AddExamState, AddExamEffects>.setLoading(loading: Boolean) {
        reduce { state.copy(isLoading = loading) }
    }
}