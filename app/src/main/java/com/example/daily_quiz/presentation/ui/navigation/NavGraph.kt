package com.example.daily_quiz.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.daily_quiz.presentation.ui.screen.QuizScreen
import com.example.daily_quiz.presentation.ui.screen.ResultScreen
import com.example.daily_quiz.presentation.ui.screen.StartScreen
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel

@Composable
fun QuizNavGraph() {
    val navController = rememberNavController()
    val viewModel: QuizViewModel = viewModel()

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
                }
            )
        }
    }
}