package com.example.daily_quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.daily_quiz.presentation.ui.screen.StartScreen
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel
import com.example.daily_quiz.ui.theme.Daily_QuizTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.daily_quiz.presentation.navigation.QuizNavGraph

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