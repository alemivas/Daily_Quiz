package com.example.daily_quiz.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: LocalDateTime,
    val totalCorrect: Int,
    val totalQuestions: Int,

)

@Entity(tableName = "quiz_questions")
data class QuizQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val resultId: Int, // Связь с QuizResult
    val questionText: String,
    val correctAnswer: String,
    val userAnswer: String,
    val isCorrect: Boolean,
//    val allAnswers: List<String> // Все варианты ответов

    @ColumnInfo(name = "all_answers")
    @TypeConverters(Converters::class)
    val allAnswers: List<String>
)