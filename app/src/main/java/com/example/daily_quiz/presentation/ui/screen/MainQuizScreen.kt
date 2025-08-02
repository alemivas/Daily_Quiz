package com.example.daily_quiz.presentation.ui.screen

import androidx.compose.runtime.Composable
import com.example.daily_quiz.presentation.ui.screen.main_quiz_screen_components.MainQuizScreenQuiz
import com.example.daily_quiz.presentation.ui.screen.main_quiz_screen_components.MainQuizScreenStart
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel

@Composable
fun MainQuizScreen(
    viewModel: QuizViewModel
) {

    if (!viewModel.isQuizStart.value) {
        MainQuizScreenStart(
            isLoading = viewModel.isLoading.value,
            isError = viewModel.isError.value,
            onStartQuiz = {
                viewModel.loadQuestions()
//            navController.navigate("quiz")
            },
//            isQuizStart = viewModel.isQuizStart.value,
            viewModel = viewModel,
        )
    } else {
        MainQuizScreenQuiz(
            viewModel = viewModel,
//            onQuizComplete = { navController.navigate("results") }
            onQuizComplete = { /*navController.navigate("results")*/ }
        )
    }


}