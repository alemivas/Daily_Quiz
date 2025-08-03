package com.example.daily_quiz

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daily_quiz.data.local.QuizDatabase
import com.example.daily_quiz.data.repository.QuizRepositoryImpl
import com.example.daily_quiz.presentation.navigation.QuizNavGraph
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel
import com.example.daily_quiz.ui.theme.Daily_QuizTheme

class MainActivity : ComponentActivity() {
//    private val viewModel: QuizViewModel by viewModels(factoryProducer = {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                val database = QuizDatabase.getDatabase(this@MainActivity)
//                return QuizViewModel(
//                    repository = QuizRepositoryImpl(),
//                    resultDao = database.resultDao()
//                ) as T
//            }
//        }
//    })

    private val viewModel: QuizViewModel by viewModels {
        val database = QuizDatabase.getDatabase(this)
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuizViewModel(
                    repository = QuizRepositoryImpl(),
                    resultDao = database.resultDao()
                ) as T
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

//            val application = application as QuizApplication
//            val viewModel: QuizViewModel = viewModel(
//                factory = object : ViewModelProvider.Factory {
//                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                        return QuizViewModel(
//                            repository = QuizRepositoryImpl(),
//                            resultDao = application.database.resultDao()
//                        ) as T
//                    }
//                }
//            )

            Daily_QuizTheme {

                QuizNavGraph(viewModel)

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