package com.example.daily_quiz.presentation.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.data.local.QuizQuestion
import com.example.daily_quiz.data.local.QuizResult
import com.example.daily_quiz.data.local.QuizResultWithQuestions
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: QuizViewModel,
    onBack: () -> Unit,
    onDetailClick: (Int) -> Unit
) {
    val history by viewModel.fullHistory.collectAsState(initial = emptyList())

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

@Composable
private fun EmptyHistoryMessage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("История прохождений пуста")
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
                )

                Text(
                    text = "${result.totalCorrect}/${result.totalQuestions}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}