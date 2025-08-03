package com.example.daily_quiz.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_quiz.data.local.QuizResult
import com.example.daily_quiz.data.local.QuizResultDao
import com.example.daily_quiz.data.repository.QuizRepositoryImpl
import com.example.daily_quiz.domain.model.Question
import com.example.daily_quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class QuizViewModel(
    private val repository: QuizRepository = QuizRepositoryImpl(),
    private val resultDao: QuizResultDao
) : ViewModel() {

    val results: Flow<List<QuizResult>> = resultDao.getAllResults()

    private val _userAnswers = mutableMapOf<Int, String>() // Вопрос -> Ответ

    // Состояния
    private val _questions = mutableStateListOf<Question>()
    private val _currentQuestionIndex = mutableStateOf(0)
    private val _selectedAnswer = mutableStateOf<String?>(null)
    private val _isQuizCompleted = mutableStateOf(false)

    // Геттеры
    val currentQuestion: Question?
        get() = _questions.getOrNull(_currentQuestionIndex.value)
    val currentQuestionNumber: Int
        get() = _currentQuestionIndex.value + 1
    val totalQuestions: Int
        get() = _questions.size
    val selectedAnswer: String?
        get() = _selectedAnswer.value
    val isQuizCompleted: Boolean
        get() = _isQuizCompleted.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError

    fun loadQuestions() {
        viewModelScope.launch {
            _isLoading.value = true
//            _isError.value = false

            try {
                _questions.clear()
                _questions.addAll(repository.getQuestions())
                _currentQuestionIndex.value = 0
                _isQuizCompleted.value = false
            } catch(e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }

//            try {
//                val questions = repository.getQuestions()
//                // TODO: Сохранить вопросы и перейти к викторине
//            } catch (e: Exception) {
//                _isError.value = true
//            } finally {
//                _isLoading.value = false
//            }
        }
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
        _userAnswers[_currentQuestionIndex.value] = answer
    }

    fun moveToNextQuestion() {
        if (_currentQuestionIndex.value < _questions.size - 1) {
            _currentQuestionIndex.value++
            _selectedAnswer.value = null
        } else {
            _isQuizCompleted.value = true
        }
    }

    fun calculateScore(): Int {
        return _questions.mapIndexed { index, question ->
            if (_userAnswers[index] == question.correctAnswer) 1 else 0
        }.sum()
    }

    fun resetQuiz() {
        _currentQuestionIndex.value = 0
        _selectedAnswer.value = null
        _userAnswers.clear()
        _isQuizCompleted.value = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveResult(correctAnswers: Int, totalQuestions: Int) {
        viewModelScope.launch {
            resultDao.insert(
                QuizResult(
                    date = LocalDateTime.now(),
                    correctAnswers = correctAnswers,
                    totalQuestions = totalQuestions
                )
            )
        }
    }
}