package com.kk.eazypariksha.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kk.eazypariksha.android.navigation.EpNavHost
import com.kk.samplecomposeapp.ui.theme.EazyParikshaTheme

@Composable
fun EpApp() {
    EazyParikshaTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            color = MaterialTheme.colors.background,
        ) { EpNavHost(navController = navController) }
    }
}