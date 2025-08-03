# DailyQuiz - Тестовое задание

Мобильное приложение для прохождения викторин с возможностью просмотра истории результатов.

## 📱 Скриншоты

| Стартовый экран | Викторина | Результаты | История |
|----------------|-----------|------------|---------|
| ![Start Screen](screenshots/start.png) | ![Quiz Screen](screenshots/quiz.png) | ![Results Screen](screenshots/results.png) | ![History Screen](screenshots/history.png) |

## 📌 Функционал

- 🎯 Прохождение викторины (5 вопросов)
- 📊 Сохранение результатов в локальную БД
- 🕒 Просмотр истории прохождений с датой
- 🔍 Детализация по каждому прохождению
- ♻️ Возможность начать заново

## 🛠 Технологии

- **Архитектура**: MVVM + Clean Architecture
- **Асинхронность**: Kotlin Coroutines
- **Локальное хранилище**: Room Database
- **UI**: Jetpack Compose
- **Сеть**: Retrofit
- **Навигация**: Compose Navigation
- **DI**: Hilt (опционально)

## 📦 Установка

1. Клонируйте репозиторий:
```bash
git clone https://github.com/yourusername/DailyQuiz.git
Откройте проект в Android Studio (Electric Eel или новее)

Запустите на эмуляторе или физическом устройстве (API 24+)

🌐 Используемое API
Приложение использует Open Trivia DB:

text
https://opentdb.com/api.php?amount=5&type=multiple
🏗 Структура проекта
text
com.example.dailyquiz
├── data
│   ├── local    # Room entities, DAOs
│   ├── remote   # Retrofit, API models
│   └── repository
├── domain       # Бизнес-логика
└── presentation
    ├── ui       # Compose компоненты
    └── viewmodel
🔧 Настройка
Для кастомизации викторины измените параметры в QuizRepositoryImpl:

kotlin
api.getQuestions(
    amount = 5,
    category = 9,      // General Knowledge
    difficulty = "easy",
    type = "multiple"  // Multiple choice
)
📄 Лицензия
text
MIT License
Copyright (c) 2023 Your Name
🤝 Контакты
По вопросам сотрудничества:
📧 your.email@example.com
🔗 LinkedIn Profile