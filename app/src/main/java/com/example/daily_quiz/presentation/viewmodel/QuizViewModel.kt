package com.example.daily_quiz.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_quiz.data.repository.QuizRepositoryImpl
import com.example.daily_quiz.domain.repository.QuizRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State


class QuizViewModel(
    private val repository: QuizRepository = QuizRepositoryImpl()
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError

    fun loadQuestions() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false

            try {
                val questions = repository.getQuestions()
                // TODO: Сохранить вопросы и перейти к викторине
            } catch (e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}