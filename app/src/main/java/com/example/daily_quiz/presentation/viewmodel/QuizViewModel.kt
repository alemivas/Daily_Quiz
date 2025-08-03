package com.example.daily_quiz.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_quiz.data.local.QuizHistoryDao
import com.example.daily_quiz.data.local.QuizQuestion
import com.example.daily_quiz.data.local.QuizResult
import com.example.daily_quiz.data.local.QuizResultWithQuestions
import com.example.daily_quiz.data.repository.QuizRepositoryImpl
import com.example.daily_quiz.domain.model.Question
import com.example.daily_quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class QuizViewModel(
    private val repository: QuizRepository = QuizRepositoryImpl(),
    private val historyDao: QuizHistoryDao
) : ViewModel() {

    // Получение полной истории
    val fullHistory: Flow<List<QuizResultWithQuestions>> = historyDao.getAllResultsWithQuestions()

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

            try {
                _questions.clear()
                _questions.addAll(repository.getQuestions())
                _userAnswers.clear()
                _currentQuestionIndex.value = 0
                _isQuizCompleted.value = false
            } catch(e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
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
    fun saveFullQuizResult(
        correctAnswers: Int,
        questions: List<Question> = _questions,
        userAnswers: Map<Int, String> = _userAnswers,  // key - question index, value - answer
    ) {
        viewModelScope.launch {
            // Сохраняем основной результат
            val resultId = historyDao.insertResult(
                QuizResult(
                    date = LocalDateTime.now(),
                    totalCorrect = correctAnswers,
                    totalQuestions = questions.size
                )
            )

            // Сохраняем все вопросы
            val quizQuestions = questions.mapIndexed { index, question ->
                QuizQuestion(
                    resultId = resultId.toInt(),
                    questionText = question.text,
                    correctAnswer = question.correctAnswer,
                    userAnswer = userAnswers[index] ?: "",
                    isCorrect = userAnswers[index] == question.correctAnswer,
                    allAnswers = (question.incorrectAnswers + question.correctAnswer).shuffled()
                )
            }

            historyDao.insertQuestions(quizQuestions)
        }
    }

    private val _currentResult = MutableStateFlow<QuizResultWithQuestions?>(null)
    val currentResult: StateFlow<QuizResultWithQuestions?> = _currentResult.asStateFlow()

    fun loadResultDetails(resultId: Int) {
        viewModelScope.launch {
            _currentResult.value = historyDao.getResultById(resultId)
        }
    }
}