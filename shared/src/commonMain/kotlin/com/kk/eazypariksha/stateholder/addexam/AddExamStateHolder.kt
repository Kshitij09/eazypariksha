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

abstract class AddExamStateHolder(
    coroutineScope: CoroutineScope
) : BaseContainerHost<AddExamState, Unit>(coroutineScope) {
    open fun setSelectedSubject(index: Int) {}
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

    private suspend fun SimpleSyntax<AddExamState, Unit>.setLoading(loading: Boolean) {
        reduce { state.copy(isLoading = loading) }
    }
}