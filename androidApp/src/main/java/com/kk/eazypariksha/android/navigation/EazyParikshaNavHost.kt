package com.kk.eazypariksha.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kk.eazypariksha.android.addexam.AddExam
import com.kk.eazypariksha.android.home.Home
import com.kk.eazypariksha.ui.StringConstant

interface Destination {
    val parent: Destination?
    val route: String
    val absoluteRoute: String
        get() {
            return buildString {
                parent?.let { append("${it.route}/") }
                append(route)
            }
        }
}

object EpRoute {
    object Home : Destination {
        override val parent = null
        override val route = "home"


        object AddExam : Destination {
            override val parent = Home
            override val route = "addExam"
        }
    }


}

@Composable
fun EpNavHost(
    modifier: Modifier = Modifier,
    setAppTitle: (String) -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = EpRoute.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.absoluteRoute,
        modifier = modifier
    ) {
        destComposable(EpRoute.Home) {
            SideEffect { setAppTitle(StringConstant.easyPariksha) }
            Home { navController.navigate(it) }
        }
        destComposable(EpRoute.Home.AddExam) {
            SideEffect { setAppTitle(StringConstant.addExam) }
            AddExam()
        }
    }
}

internal fun NavController.navigate(
    destination: Destination,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(destination.absoluteRoute, navOptions, navigatorExtras)
}

internal fun NavGraphBuilder.destComposable(
    destination: Destination,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(destination.absoluteRoute, arguments, deepLinks, content)