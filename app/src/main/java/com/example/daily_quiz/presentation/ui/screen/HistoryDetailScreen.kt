package com.example.daily_quiz.presentation.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.data.local.QuizQuestion
import com.example.daily_quiz.data.local.QuizResultWithQuestions
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    viewModel: QuizViewModel,
    resultId: Int,
//    resultWithQuestions: QuizResultWithQuestions,
    onBack: () -> Unit
) {
    // Загружаем данные при входе
    LaunchedEffect(resultId) {
        viewModel.loadResultDetails(resultId)
    }

    val result by viewModel.currentResult.collectAsState()

//    Column {
//        TopAppBar(
//            title = { Text("Прохождение от ${resultWithQuestions.result.date.format(
//                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}") },
//            navigationIcon = {
//                IconButton(onClick = onBack) {
//                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
//                }
//            }
//        )
//
//        LazyColumn {
//            items(resultWithQuestions.questions) { question ->
//                QuestionResultItem(question)
//            }
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали прохождения") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        when {
            result == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                HistoryDetailContent(
                    result = result!!,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

//@Composable
//fun QuestionResultItem(question: QuizQuestion) {
//    Card {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(question.questionText, fontWeight = FontWeight.Bold)
//            Text("Правильный ответ: ${question.correctAnswer}")
//            Text("Ваш ответ: ${question.userAnswer}",
//                color = if (question.isCorrect) Color.Green else Color.Red)
//        }
//    }
//}

@Composable
private fun HistoryDetailContent(
    result: QuizResultWithQuestions,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
//        item {
//            ResultHeader(result.result)
//        }
        items(result.questions) { question ->
            QuestionDetailItem(question)
        }
    }
}

@Composable
private fun QuestionDetailItem(question: QuizQuestion) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = question.questionText,
//                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ваш ответ: ${question.userAnswer}",
                color = if (question.isCorrect) Color.Green else Color.Red
//                color = Color.Red
            )
            Text(text = "Правильный ответ: ${question.correctAnswer}")
        }
    }
}