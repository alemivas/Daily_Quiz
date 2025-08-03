# DailyQuiz

Мобильное приложение для прохождения викторин с возможностью просмотра истории результатов.

## 📱 Скриншоты

| Стартовый экран | Викторина | Результаты | История | Детальная История                                        |
|-----------------|-----------|------------|---------|----------------------------------------------------------|
| ![Start Screen](screenshots/start.png) | ![Quiz Screen](screenshots/quiz.png) | ![Results Screen](screenshots/results.png) | ![History Screen](screenshots/history.png) | ![Detail History Screen](screenshots/detail_history.png) |

## 📌 Функционал

- 🎯 Прохождение викторины (5 вопросов)
- 📊 Сохранение результатов в локальную БД
- 🕒 Просмотр истории прохождений с датой
- 🔍 Детализация по каждому прохождению

## 🛠 Технологии

- **Архитектура**: MVVM + Clean Architecture
- **Асинхронность**: Kotlin Coroutines
- **Локальное хранилище**: Room Database
- **UI**: Jetpack Compose
- **Сеть**: Retrofit
- **Навигация**: Compose Navigation

## 🌐 Используемое API
Приложение использует Open Trivia DB: https://opentdb.com/

## 🏗 Структура проекта
com.example.daily_quiz
├── data
│   ├── local       # Room entities, DAOs
│   ├── remote      # Retrofit, API models
│   └── repository
├── domain
└── presentation
    ├── navigation  # Навигация
    ├── ui          # Compose компоненты
    └── viewmodel

## 🔧 Настройка
Для кастомизации викторины измените параметры в data/remote/ApiService:

## 🤝 Контакты
📧 a_36524@mail.ru