package com.example.daily_quiz.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.daily_quiz.data.local.QuizResultWithQuestions
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
//    val navController = rememberNavController()
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
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDetailClick = { resultId ->
                    navController.navigate("history_detail/$resultId")
                }
            )
        }

//        composable(
//            "history_detail/{resultId}",
//            arguments = listOf(navArgument("resultId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val resultId = backStackEntry.arguments?.getInt("resultId") ?: 0
////            val result = viewModel.fullHistory.first { it.result.id == resultId }
//            val result = viewModel.getResultById(resultId)
//            if (result != null) {
//                HistoryDetailScreen(
//                    resultWithQuestions = result,
//                    onBack = { navController.popBackStack() }
//                )
//            }
//        }

        composable("history_detail/{resultId}") { backStackEntry ->
            val resultId = backStackEntry.arguments?.getInt("resultId") ?: 0
            var result by remember { mutableStateOf<QuizResultWithQuestions?>(null) }
            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(resultId) {
                result = viewModel.getResultById(resultId)
                isLoading = false
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                result?.let {
                    HistoryDetailScreen(
                        resultWithQuestions = it,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}