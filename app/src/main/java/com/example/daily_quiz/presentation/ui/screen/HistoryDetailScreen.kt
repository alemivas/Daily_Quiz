package com.example.daily_quiz.presentation.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.data.local.QuizQuestion
import com.example.daily_quiz.data.local.QuizResultWithQuestions
import com.example.daily_quiz.utils.cleanHtml

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    resultWithQuestions: QuizResultWithQuestions,
//    viewModel: QuizViewModel,
//    resultId: Int,
//    resultWithQuestions: QuizResultWithQuestions,
    onBack: () -> Unit
) {
//    // Загружаем данные при входе
//    LaunchedEffect(resultId) {
//        viewModel.loadResultDetails(resultId)
//    }
//
//    val result by viewModel.currentResult.collectAsState()

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
//        when {
//            result == null -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(padding),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//            else -> {
//                HistoryDetailContent(
//                    result = result!!,
//                    modifier = Modifier.padding(padding)
//                )
//            }
//        }
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
//            item {
//                ResultHeader(resultWithQuestions.result)
//            }

            items(resultWithQuestions.questions) { question ->
                QuestionWithAnswers(
                    question = question,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
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

@Composable
fun QuestionWithAnswers(
    question: QuizQuestion,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
//        elevation = 4.dp,
//        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Текст вопроса
            Text(
                text = question.questionText.cleanHtml(),
//                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Все варианты ответов
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                question.allAnswers.forEach { answer ->
                    AnswerItem(
                        answer = answer,
                        isCorrect = answer == question.correctAnswer,
                        isSelected = answer == question.userAnswer
                    )
                }
            }

            // Статус ответа
            Text(
                text = if (question.isCorrect) "✓ Вы ответили правильно"
                else "✗ Вы ошиблись",
                color = if (question.isCorrect) Color(0xFF4CAF50)
                else Color(0xFFF44336),
//                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun AnswerItem(
    answer: String,
    isCorrect: Boolean,
    isSelected: Boolean
) {
    val backgroundColor = when {
        isSelected && isCorrect -> Color(0xFFE8F5E9).copy(alpha = 0.8f)
        isSelected -> Color(0xFFFFEBEE).copy(alpha = 0.8f)
        isCorrect -> Color(0xFFE8F5E9).copy(alpha = 0.4f)
//        else -> MaterialTheme.colors.surface
        else -> Color.Black
    }

    val borderColor = when {
        isSelected && isCorrect -> Color(0xFF4CAF50)
        isSelected -> Color(0xFFF44336)
        isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.5f)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Иконка статуса
        Icon(
            imageVector = when {
                isSelected && isCorrect -> Icons.Default.CheckCircle
//                isSelected -> Icons.Default.HighlightOff
                isSelected -> Icons.Default.CheckCircle
                isCorrect -> Icons.Default.Check
//                else -> Icons.Default.RadioButtonUnchecked
                else -> Icons.Default.CheckCircle
            },
            contentDescription = null,
            tint = when {
                isSelected && isCorrect -> Color(0xFF4CAF50)
                isSelected -> Color(0xFFF44336)
                isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.5f)
//                else -> MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                else -> Color.Black
            },
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Текст ответа
        Text(
            text = answer.cleanHtml(),
//            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
    }
}