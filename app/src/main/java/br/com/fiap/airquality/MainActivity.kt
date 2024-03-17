package br.com.fiap.airquality

import FormScreen
import ResultScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.airquality.ui.theme.AirQualityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirQualityTheme(
                darkTheme = false,
                background = painterResource(id = R.drawable.background)
            ) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "FormScreen") {
                    composable("FormScreen") {
                        FormScreen(navController)
                    }
                    composable(
                        "ResultScreen/{city}/{state}/{aqi}",
                        arguments = listOf(
                            navArgument("city") { type = NavType.StringType },
                            navArgument("state") { type = NavType.StringType },
                            navArgument("aqi") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val city = backStackEntry.arguments?.getString("city") ?: ""
                        val state = backStackEntry.arguments?.getString("state") ?: ""
                        val aqi = backStackEntry.arguments?.getInt("aqi") ?: 0
                        ResultScreen(city = city, state = state, aqi = aqi)
                    }
                }
            }
        }
    }
}


