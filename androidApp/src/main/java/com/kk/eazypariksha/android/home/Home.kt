package com.kk.eazypariksha.android.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kk.eazypariksha.ui.StringConstant
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme

@Composable
fun Home() {
    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            ) {
                Box(contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = StringConstant.easyPariksha,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                    )
                }
            }
        },
        contentColor = MaterialTheme.colors.onBackground,
    ) {
        Column {
            ActionRow(
                icon = rememberVectorPainter(image = Icons.Default.Add),
                title = StringConstant.addExam
            )
            ActionRow(
                icon = rememberVectorPainter(image = Icons.Default.Drafts),
                title = StringConstant.drafts
            )
            ActionRow(
                icon = rememberVectorPainter(image = Icons.Default.SyncLock),
                title = StringConstant.upcomingExams
            )
            ActionRow(
                icon = rememberVectorPainter(image = Icons.Default.FactCheck),
                title = StringConstant.results
            )
            ActionRow(
                icon = rememberVectorPainter(image = Icons.Default.PersonSearch),
                title = StringConstant.browseStudents
            )
        }
    }
}

@Composable
fun ActionRow(
    icon: Painter,
    title: String,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                modifier = Modifier.size(32.dp),
                tint = Color.DarkGray,
                contentDescription = null
            )
            Text(
                text = title,
                style = MaterialTheme.typography.h4,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
        Divider()
    }
}

@Preview
@Composable
fun HomePreview() {
    EazyParikshaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) { Home() }
    }
}
