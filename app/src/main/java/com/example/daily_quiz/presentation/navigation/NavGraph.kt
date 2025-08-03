package com.example.daily_quiz.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.daily_quiz.presentation.ui.screen.HistoryScreen
import com.example.daily_quiz.presentation.ui.screen.QuizFlowScreen.QuizScreen
import com.example.daily_quiz.presentation.ui.screen.QuizFlowScreen.ResultScreen
import com.example.daily_quiz.presentation.ui.screen.QuizFlowScreen.StartScreen
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuizNavGraph(viewModel: QuizViewModel) {
    val navController = rememberNavController()
//    val viewModel: QuizViewModel = viewModel()

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
                viewModel = viewModel(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}