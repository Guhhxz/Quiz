package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quiz.Screen.QuizScreen
import com.example.quiz.Screen.ResultScreen
import com.example.quiz.Screen.StartScreen
import com.example.quiz.ui.theme.QuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "start",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            )
                        },
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("start") {
                            StartScreen(navController = navController)
                        }
                        composable("quiz") {
                            QuizScreen(navController = navController)
                        }
                        composable(
                            route = "result/{acertos}/{total}",
                            arguments = listOf(
                                navArgument("acertos") { type = NavType.IntType },
                                navArgument("total") { type = NavType.IntType }
                            )
                        ) { entry ->
                            ResultScreen(
                                navController = navController,
                                acertos = entry.arguments?.getInt("acertos") ?: 0,
                                totalPerguntas = entry.arguments?.getInt("total") ?: 0
                            )
                        }
                    }
                }
            }
        }
    }
}