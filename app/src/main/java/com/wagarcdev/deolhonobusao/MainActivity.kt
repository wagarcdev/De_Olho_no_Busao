package com.wagarcdev.deolhonobusao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wagarcdev.deolhonobusao.navigation.AppNavigation
import com.wagarcdev.deolhonobusao.presentention.ui.theme.DeOlhoNoBusaoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {

            setKeepOnScreenCondition { viewModel.isLoading.value }



        }

        setContent {
            DeOlhoNoBusaoTheme {

                AppNavigation()

            }
        }
    }

}
