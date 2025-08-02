package com.example.daily_quiz.data.repository

import com.example.daily_quiz.domain.model.Question
import com.example.daily_quiz.domain.repository.QuizRepository
import com.example.daily_quiz.data.remote.RetrofitInstance

class QuizRepositoryImpl : QuizRepository {
    private val api = RetrofitInstance.api

    override suspend fun getQuestions(): List<Question> {
        val response = api.getQuestions()
        return response.results.map { dto ->
            Question(
                text = dto.question,
                correctAnswer = dto.correct_answer,
                incorrectAnswers = dto.incorrect_answers,
                difficulty = dto.difficulty
            )
        }
    }
}