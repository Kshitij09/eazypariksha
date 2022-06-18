package com.kk.eazypariksha.android.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kk.eazypariksha.android.navigation.Destination
import com.kk.eazypariksha.android.navigation.EpRoute
import com.kk.eazypariksha.ui.StringConstant
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigate: (Destination) -> Unit,
) {
    Column(modifier = modifier) {
        ActionRow(
            icon = rememberVectorPainter(image = Icons.Default.Add),
            title = StringConstant.addExam,
            onClick = { navigate(EpRoute.Home.AddExam) }
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

@Composable
fun ActionRow(
    icon: Painter,
    title: String,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = icon,
                modifier = Modifier.size(32.dp),
                contentDescription = null
            )
            Text(
                text = title,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 10.dp)
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
        ) { HomeScreen {} }
    }
}
