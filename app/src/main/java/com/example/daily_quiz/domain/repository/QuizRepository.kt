package com.example.daily_quiz.domain.repository

import com.example.daily_quiz.domain.model.Question

interface QuizRepository {
    suspend fun getQuestions(): List<Question>
}