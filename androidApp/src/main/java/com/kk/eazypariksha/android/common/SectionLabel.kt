package com.kk.eazypariksha.android.common

import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SectionLabel(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        color = LocalContentColor.current.copy(alpha = 0.6f),
        style = MaterialTheme.typography.h5,
        modifier = modifier
    )
}