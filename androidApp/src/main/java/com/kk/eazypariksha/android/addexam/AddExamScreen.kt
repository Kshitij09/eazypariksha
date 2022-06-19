package com.kk.eazypariksha.android.addexam

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.kk.eazypariksha.android.common.SaveAndNextFooter
import com.kk.eazypariksha.model.FakeData
import com.kk.eazypariksha.model.exam.Subject
import com.kk.eazypariksha.stateholder.addexam.AddExamEffects
import com.kk.eazypariksha.stateholder.addexam.AddExamState
import com.kk.eazypariksha.stateholder.addexam.AddExamStateHolder
import com.kk.eazypariksha.stateholder.addexam.FeedbackEffect
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AddExamScreen(
    scaffoldState: ScaffoldState,
    stateHolder: AddExamStateHolder,
    navigateUp: () -> Unit,
    onNext: () -> Unit
) {
    val state by stateHolder.stateFlow().collectAsState()
    val subjectPickerEnabled by derivedStateOf { state.subjects.isNotEmpty() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
    var dateTimeLabel by remember {
        val placeholder = "Select Date & Time"
        val dateTime = state.dateTime?.toJavaLocalDateTime()?.format(formatter)
        mutableStateOf(dateTime ?: placeholder)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            SectionLabel(label = "Subject", modifier = Modifier.padding(vertical = 16.dp))
            SubjectComponent(
                subjectPickerEnabled,
                state.selectedSubject,
                state.subjects,
            ) { stateHolder.setSelectedSubject(it) }
            SectionLabel(
                label = "Date & Time",
                modifier = Modifier.padding(top = 48.dp, bottom = 16.dp)
            )
            PlaceholderCard(
                leadingIcon = rememberVectorPainter(image = Icons.Default.Timer),
                label = dateTimeLabel,
                modifier = Modifier
                    .clickable {
                        MaterialDialog(context).show {
                            dateTimePicker(requireFutureDateTime = true) { _, calendar ->
                                stateHolder.setDateTime(calendar.toLocalDateTime())
                                val datetime = calendar.tojLocalDateTime()
                                dateTimeLabel = datetime.format(formatter)
                            }
                        }
                    }
            )
        }

        SaveAndNextFooter(
            onSave = {
                stateHolder.saveDraft()
                navigateUp()
            },
            onNext = onNext
        )
    }

    DisposableEffect(key1 = stateHolder) {
        val sideEffectJob = scope.launch {
            stateHolder.sideFlow().collect { effect ->
                when (effect) {
                    is AddExamEffects.DraftSaved -> {
                        scaffoldState.snackbarHostState.showSnackbar(effect.feedback)
                        navigateUp()
                    }
                    is FeedbackEffect -> {
                        scaffoldState.snackbarHostState.showSnackbar(effect.feedback)
                    }
                    else -> {}
                }
            }
        }
        onDispose { sideEffectJob.cancel() }
    }

}

@Composable
fun PlaceholderCard(
    modifier: Modifier = Modifier,
    leadingIcon: Painter,
    label: String,
) {
    val cardShape = RoundedCornerShape(8.dp)
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
        shape = cardShape,
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Icon(
                painter = leadingIcon,
                contentDescription = null,
                modifier = Modifier.padding(start = 12.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}


@Composable
fun SectionLabel(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        color = LocalContentColor.current.copy(alpha = 0.6f),
        style = MaterialTheme.typography.h5,
        modifier = modifier
    )
}

@Composable
fun ColumnScope.SubjectComponent(
    enabled: Boolean,
    selectedSubject: Subject? = null,
    subjectList: List<Subject>,
    onOptionSelected: (Int) -> Unit
) {
    var subjectPickerExpanded by remember { mutableStateOf(false) }
    Box {
        PlaceholderCard(
            leadingIcon = rememberVectorPainter(image = Icons.Default.ArrowDropDown),
            label = selectedSubject?.name ?: "",
            modifier = Modifier.clickable(enabled = enabled && selectedSubject != null) {
                subjectPickerExpanded = true
            }
        )

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
    val scaffoldState = rememberScaffoldState()
    val stateHolder = object : AddExamStateHolder(scope) {
        override val initialState = AddExamState(subjects = FakeData.subjects)
    }

    EazyParikshaTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddExamScreen(scaffoldState, stateHolder, {}, {})
        }
    }
}


private fun Calendar.tojLocalDateTime(): java.time.LocalDateTime {
    return java.time.LocalDateTime.of(
        get(Calendar.YEAR),
        get(Calendar.MONTH),
        get(Calendar.DAY_OF_MONTH),
        get(Calendar.HOUR),
        get(Calendar.MINUTE),
        get(Calendar.SECOND),
        get(Calendar.MILLISECOND) * 1000
    )
}

private fun Calendar.toLocalDateTime(): LocalDateTime {
    return LocalDateTime(
        get(Calendar.YEAR),
        get(Calendar.MONTH),
        get(Calendar.DAY_OF_MONTH),
        get(Calendar.HOUR),
        get(Calendar.MINUTE),
        get(Calendar.SECOND),
        get(Calendar.MILLISECOND) * 1000
    )
}