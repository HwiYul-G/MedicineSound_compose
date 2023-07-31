@file:OptIn(ExperimentalMaterial3Api::class)

package com.y.medicinesound_compose.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.y.medicinesound_compose.R
import com.y.medicinesound_compose.ui.screens.BasicScreen
import com.y.medicinesound_compose.ui.screens.CameraScreen
import com.y.medicinesound_compose.ui.theme.MedicineSound_composeTheme
import com.y.medicinesound_compose.utils.NavDestination


@ExperimentalMaterial3Api
@Composable
fun MedicineSoundApp(
    navController: NavHostController = rememberNavController(),
) {
    // get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // get current route
    val currentScreen: String = backStackEntry?.destination?.route ?: NavDestination.BASIC_SCREEN



    Scaffold(
        topBar = {
            MedicineSoundTopAppBar(
                titleResId = R.string.app_name,
                canNavigationBack = currentScreen != NavDestination.BASIC_SCREEN , //TODO : 임시로
                onNavigationClicked = { navController.navigateUp() },
                onMoreClicked = { /*TODO*/ }
            )

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavDestination.BASIC_SCREEN,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = NavDestination.BASIC_SCREEN) {
                BasicScreen(navController)
            }

            composable(route = NavDestination.CAMERA_SCREEN) {
                CameraScreen(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineSoundTopAppBar(
    @StringRes titleResId: Int,
    canNavigationBack: Boolean,
    onNavigationClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(stringResource(titleResId)) },
        navigationIcon = {
            if (canNavigationBack) {
                IconButton(onClick = onNavigationClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onMoreClicked) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MedicineSoundAppPreview() {
    MedicineSound_composeTheme {
        MedicineSoundApp()
    }
}