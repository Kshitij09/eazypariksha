package com.kk.eazypariksha.android.addquestion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kk.eazypariksha.android.common.SectionLabel
import com.kk.eazypariksha.android.common.TwoActionFooter
import com.kk.eazypariksha.stateholder.addexam.AddExamStateHolder

@Composable
fun AddQuestionScreen(
    stateHolder: AddExamStateHolder,
    navigateUp: () -> Unit,
    onDone: () -> Unit,
) {
    var question by remember { mutableStateOf("") }
    var points by remember { mutableStateOf("") }
    val options = remember { List(4) { "" }.toMutableStateList() }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TransparentTextField(
                value = question,
                onValueChange = { question = it },
                hint = "Question",
                keyboardOptions = KeyboardOptions.Default
            )
            Divider()

            TransparentTextField(
                value = points,
                onValueChange = { points = it },
                hint = "Points",
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            SectionLabel(label = "Options", modifier = Modifier.padding(start = 12.dp))
            options.forEachIndexed { index, option ->
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    TransparentTextField(
                        value = option,
                        onValueChange = { options[index] = it },
                        hint = "Option ${index + 1}",
                    )
                    Divider()
                }
            }
        }
        TwoActionFooter(
            onLeft = {
                stateHolder.submitExam()
                onDone()
            },
            onRight = { stateHolder.saveQuestion() },
            leftContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Default.Check),
                        contentDescription = null
                    )
                    Text(text = "Done", modifier = Modifier.padding(start = 6.dp))
                }
            },
            rightContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Default.Add),
                        contentDescription = null
                    )
                    Text(text = "Add More", modifier = Modifier.padding(start = 6.dp))
                }
            }
        )
    }
}

@Composable
fun TransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions? = null
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = hint) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onBackground
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions ?: KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
    )
}