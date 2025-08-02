package com.example.daily_quiz.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.daily_quiz.presentation.ui.screen.MainQuizScreen
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
            MainQuizScreen(
                viewModel = viewModel,
            )
        }
//            MainQuizScreen(
//                isLoading = viewModel.isLoading.value,
//                isError = viewModel.isError.value,
//                onStartQuiz = {
//                    viewModel.loadQuestions()
//                    navController.navigate("quiz")
//                }
//            )
//        }
//        composable("quiz") {
//            MainQuizScreenQuiz(
//                viewModel = viewModel,
//                onQuizComplete = { navController.navigate("results") }
//            )
//        }
//        composable("results") {
//            MainQuizScreenResult(
//                viewModel = viewModel,
//                onRestartQuiz = {
//                    navController.popBackStack("start", inclusive = false)
//                }
//            )
//        }
    }
}