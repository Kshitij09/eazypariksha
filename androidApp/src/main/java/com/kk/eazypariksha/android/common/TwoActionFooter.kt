package com.kk.eazypariksha.android.common

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.kk.eazypariksha.ui.StringConstant

@Composable
fun BoxScope.TwoActionFooter(
    leftLabel: String = StringConstant.save,
    rightLabel: String = StringConstant.next,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = onLeft,
            modifier = Modifier.weight(1f)
        ) {
            if (leftContent != null) {
                leftContent()
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Default.Save),
                        contentDescription = null
                    )
                    Text(text = leftLabel, modifier = Modifier.padding(start = 6.dp))
                }
            }
        }

        TextButton(
            onClick = onRight,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp)
        ) {
            if (rightContent != null) {
                rightContent()
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = rightLabel)
                }
            }
        }
    }
}