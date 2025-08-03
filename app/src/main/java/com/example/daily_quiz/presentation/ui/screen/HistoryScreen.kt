package com.example.daily_quiz.presentation.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.data.local.QuizQuestion
import com.example.daily_quiz.data.local.QuizResult
import com.example.daily_quiz.data.local.QuizResultWithQuestions
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel
import com.example.daily_quiz.utils.cleanHtml
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: QuizViewModel,
    onBack: () -> Unit,
    onDetailClick: (/*resultId: */Int) -> Unit
) {
//    val results by viewModel.results.collectAsState(initial = emptyList())
    val history by viewModel.fullHistory.collectAsState(initial = emptyList())

//    Column(modifier = Modifier.padding(16.dp)) {
//        TopAppBar(
//            title = { Text("История прохождений") },
//            navigationIcon = {
//                IconButton(onClick = onBack) {
//                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
//                }
//            }
//        )
//
//        LazyColumn {
////            items(results) { result ->
////                ResultItem(result)
////            }
//            items(history) { resultWithQuestions ->
//                HistoryItem(
//                    result = resultWithQuestions.result,
//                    questions = resultWithQuestions.questions,
////                    onItemClick = { /* Открываем детали */ }
//                    onItemClick = { onDetailClick(resultWithQuestions.result.id) } // Передаем ID
//                )
//            }
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("История") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        if (history.isEmpty()) {
            EmptyHistoryMessage(modifier = Modifier.padding(padding))
        } else {
            HistoryList(
                history = history,
                onItemClick = onDetailClick,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//private fun ResultItem(result: QuizResult) {
private fun HistoryItem(
    result: QuizResult,
    questions: List<QuizQuestion>,
    onItemClick: () -> Unit
) {
//    Card(
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onItemClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Дата: ${result.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}",
//                style = MaterialTheme.typography.caption
            )
            Text(
                text = "Правильных ответов: ${result.totalCorrect}/${result.totalQuestions}",
//                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
private fun EmptyHistoryMessage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("История прохождений пуста"/*, style = MaterialTheme.typography.h6*/)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryList(
    history: List<QuizResultWithQuestions>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(history) { resultWithQuestions ->
            HistoryListItem(
                result = resultWithQuestions.result,
                questions = resultWithQuestions.questions,
                onClick = { onItemClick(resultWithQuestions.result.id) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HistoryListItem(
    result: QuizResult,
    questions: List<QuizQuestion>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
//        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Заголовок с датой и результатом
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = result.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
//                    style = MaterialTheme.typography.body2,
//                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )

                Text(
                    text = "${result.totalCorrect}/${result.totalQuestions}",
//                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = if (result.totalCorrect.toFloat() / result.totalQuestions > 0.7f) {
                        Color.Green
                    } else {
//                        MaterialTheme.colors.primary
                        Color.Black
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Краткая сводка по вопросам
            questions.take(2).forEachIndexed { index, question ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = if (question.isCorrect) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = null,
                        tint = if (question.isCorrect) Color.Green else Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = question.questionText.cleanHtml(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (questions.size > 2) {
                Text(
                    text = "и ещё ${questions.size - 2} вопросов...",
//                    style = MaterialTheme.typography.caption,
//                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}