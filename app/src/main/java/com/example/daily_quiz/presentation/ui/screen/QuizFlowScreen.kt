package com.example.daily_quiz.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel

@Composable
fun QuizFlowScreen(
    navController: NavController, // Для навигации к другим разделам
    viewModel: QuizViewModel/* = viewModel()*/
) {
    when {
        viewModel.isLoading.value -> FullScreenLoader()
        viewModel.isError.value -> StartScreen(onStartQuiz = { viewModel.loadQuestions() })
        viewModel.isQuizCompleted -> ResultScreen(
            score = viewModel.calculateScore(),
            onRestartQuiz = { viewModel.resetQuiz() },
            onExit = { navController.popBackStack() } // Возврат к предыдущему экрану
        )
//        viewModel.questions.isNotEmpty() -> QuizScreen(viewModel)
        viewModel.hasNextQuestion -> QuizScreen(viewModel)
        else -> StartScreen(onStartQuiz = { viewModel.loadQuestions() })
    }
}

@Composable
private fun FullScreenLoader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}