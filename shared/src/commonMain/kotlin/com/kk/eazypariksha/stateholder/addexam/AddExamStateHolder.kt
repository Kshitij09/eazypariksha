package com.kk.eazypariksha.stateholder.addexam

import com.kk.eazypariksha.data.ExamRepository
import com.kk.eazypariksha.model.exam.Subject
import com.kk.eazypariksha.stateholder.BaseContainerHost
import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

data class AddExamState(
    val subjects: List<Subject> = emptyList(),
    val selectedSubjectIndex: Int = 0,
    val isLoading: Boolean = false,
)

open class AddExamStateHolder(
    coroutineScope: CoroutineScope,
    private val repository: ExamRepository,
    override val initialState: AddExamState = AddExamState()
) : BaseContainerHost<AddExamState, Unit>(coroutineScope) {
    override fun onCreate(state: AddExamState) {
        super.onCreate(state)
        loadSubjects()
    }

    private fun loadSubjects() = intent {
        setLoading(true)
        val subjects = repository.getAllSubjects()
        reduce { state.copy(subjects = subjects) }
        setLoading(false)
    }

    private suspend fun SimpleSyntax<AddExamState, Unit>.setLoading(loading: Boolean) {
        reduce { state.copy(isLoading = loading) }
    }
}