package com.kk.eazypariksha.android.addexam

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kk.eazypariksha.stateholder.addexam.AddExamState
import com.kk.eazypariksha.stateholder.addexam.AddExamStateHolder
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme

@Composable
fun AddExamScreen(stateHolder: AddExamStateHolder) {
    val state by stateHolder.stateFlow().collectAsState()
    val subjectPickerEnabled by derivedStateOf { state.subjects.isNotEmpty() }
    Column {
        if (state.isLoading) {
            Text("Loading", style = MaterialTheme.typography.subtitle1)
        }
        if (subjectPickerEnabled) {
            Box(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colors.onBackground)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = state.subjects[state.selectedSubjectIndex].name,
                    modifier = Modifier.padding(start = 6.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun AddExamPreview() {
    val scope = rememberCoroutineScope()
    val stateHolder = object : AddExamStateHolder(scope) {
        override val initialState = AddExamState()
    }

    EazyParikshaTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddExamScreen(stateHolder)
        }
    }
}