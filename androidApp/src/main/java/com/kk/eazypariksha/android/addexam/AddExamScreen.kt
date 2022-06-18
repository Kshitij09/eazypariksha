package com.kk.eazypariksha.android.addexam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kk.eazypariksha.data.ExamRepository
import com.kk.eazypariksha.model.exam.Exam
import com.kk.eazypariksha.model.exam.ExamRequest
import com.kk.eazypariksha.model.exam.Subject
import com.kk.eazypariksha.stateholder.addexam.AddExamStateHolder
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme

@Composable
fun AddExamScreen(stateHolder: AddExamStateHolder) {
    val state by stateHolder.stateFlow().collectAsState()
    Column {
        if (state.isLoading) {
            Text("Loading", style = MaterialTheme.typography.subtitle1)
        }
        state.subjects.forEach {
            Text(text = it.name)
        }
    }
}

@Preview
@Composable
fun AddExamPreview() {
    val repo = object : ExamRepository {
        override suspend fun create(examRequest: ExamRequest) {
            TODO("Not yet implemented")
        }

        override suspend fun saveDraft(examRequest: ExamRequest) {
            TODO("Not yet implemented")
        }

        override suspend fun getOngoing(): List<Exam> {
            TODO("Not yet implemented")
        }

        override suspend fun getDrafts(): List<Exam> {
            TODO("Not yet implemented")
        }

        override suspend fun getAllSubjects(): List<Subject> = emptyList()

    }
    val scope = rememberCoroutineScope()
    val stateHolder = remember { AddExamStateHolder(scope, repo) }
    EazyParikshaTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddExamScreen(stateHolder)
        }
    }
}