package com.example.daily_quiz.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.daily_quiz.presentation.ui.screen.HistoryDetailScreen
import com.example.daily_quiz.presentation.ui.screen.HistoryScreen
import com.example.daily_quiz.presentation.ui.screen.QuizFlowScreen.QuizScreen
import com.example.daily_quiz.presentation.ui.screen.QuizFlowScreen.ResultScreen
import com.example.daily_quiz.presentation.ui.screen.QuizFlowScreen.StartScreen
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuizNavGraph(
    viewModel: QuizViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        composable("start") {
            StartScreen(
                isLoading = viewModel.isLoading.value,
                isError = viewModel.isError.value,
                onStartQuiz = {
                    viewModel.loadQuestions()
                    navController.navigate("quiz")
                }
            )
        }
        composable("quiz") {
            QuizScreen(
                viewModel = viewModel,
                onQuizComplete = { navController.navigate("results") }
            )
        }
        composable("results") {
            ResultScreen(
                viewModel = viewModel,
                onRestartQuiz = {
                    navController.popBackStack("start", inclusive = false)
                },
                onHistory = { navController.navigate("history") }
            )
        }

        composable("history") {
            HistoryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDetailClick = { resultId ->
                    navController.navigate("history_detail/$resultId")
                }
            )
        }

        composable(
            "history_detail/{resultId}",
            arguments = listOf(navArgument("resultId") { type = NavType.IntType })
        ) { backStackEntry ->
                val resultId = backStackEntry.arguments?.getInt("resultId") ?: 0

            LaunchedEffect(resultId) {
                viewModel.loadResultDetails(resultId)
            }

            val result by viewModel.currentResult.collectAsState()

            result?.let {
                HistoryDetailScreen(
                    resultWithQuestions = it,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}