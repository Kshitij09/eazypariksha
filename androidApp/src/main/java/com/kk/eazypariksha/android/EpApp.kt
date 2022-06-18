package com.kk.eazypariksha.android

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.kk.eazypariksha.android.navigation.EpNavHost
import com.kk.eazypariksha.ui.StringConstant
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme

@Composable
fun EpApp() {
    EazyParikshaTheme {
        val navController = rememberNavController()
        val (title, setAppTitle) = remember { mutableStateOf(StringConstant.easyPariksha) }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            color = MaterialTheme.colors.background
        ) {
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
                                text = title,
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
            ) { paddingValues ->
                EpNavHost(
                    navController = navController,
                    setAppTitle = setAppTitle,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}