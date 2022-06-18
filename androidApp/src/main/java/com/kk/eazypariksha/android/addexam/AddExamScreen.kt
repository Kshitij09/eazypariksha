package com.kk.eazypariksha.android.addexam

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kk.eazypariksha.model.exam.Subject
import com.kk.eazypariksha.stateholder.addexam.AddExamState
import com.kk.eazypariksha.stateholder.addexam.AddExamStateHolder
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme

@Composable
fun AddExamScreen(stateHolder: AddExamStateHolder) {
    val state by stateHolder.stateFlow().collectAsState()
    val subjectPickerEnabled by derivedStateOf { state.subjects.isNotEmpty() }
    val cardShape = RoundedCornerShape(8.dp)
    Column {
        if (state.isLoading) {
            Text("Loading", style = MaterialTheme.typography.subtitle1)
        }
        Column {
            SubjectSection(
                subjectPickerEnabled,
                state.subjects.getOrNull(state.selectedSubjectIndex),
                state.subjects,
                cardShape,
            ) { stateHolder.setSelectedSubject(it) }
        }
    }
}

@Composable
fun SubjectSection(
    enabled: Boolean,
    selectedSubject: Subject? = null,
    subjectList: List<Subject>,
    cardShape: Shape,
    onOptionSelected: (Int) -> Unit
) {
    var subjectPickerExpanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Subject",
            color = LocalContentColor.current.copy(alpha = 0.6f),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Card(
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
            shape = cardShape,
            modifier = Modifier
                .fillMaxWidth()
                .clip(cardShape)
                .clickable(enabled = enabled && selectedSubject != null) {
                    subjectPickerExpanded = true
                }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.ArrowDropDown),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = selectedSubject?.name ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 4.dp, top = 12.dp, bottom = 12.dp)
                )
            }
        }


        DropdownMenu(
            expanded = subjectPickerExpanded,
            onDismissRequest = { subjectPickerExpanded = false }
        ) {
            subjectList.forEachIndexed { index, subject ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(index)
                    subjectPickerExpanded = false
                }) { Text(subject.name) }
            }
        }
    }
}

@Preview
@Composable
fun AddExamPreview() {
    val scope = rememberCoroutineScope()
    val stateHolder = object : AddExamStateHolder(scope) {
        override val initialState = AddExamState(subjects = emptyList())
    }

    EazyParikshaTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddExamScreen(stateHolder)
        }
    }
}