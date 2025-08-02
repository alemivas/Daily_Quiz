package com.example.daily_quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.daily_quiz.ui.theme.Daily_QuizTheme
import com.example.daily_quiz.presentation.ui.navigation.QuizNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Daily_QuizTheme {

                QuizNavGraph()

//                val viewModel: QuizViewModel = viewModel()
//                StartScreen(
//                    isLoading = viewModel.isLoading.value,
//                    isError = viewModel.isError.value,
//                    onStartQuiz = { viewModel.loadQuestions() }
//                )
            }
        }
    }
}