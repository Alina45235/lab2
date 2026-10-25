package com.example.playlistmaker.ui.navigation

// Перечисление всех экранов приложения с их маршрутами (адресами)
enum class Screen(val route: String) {
    MAIN("main"),       // главный экран (стартовый)
    SEARCH("search"),   // экран поиска
    SETTINGS("settings") // экран настроек
}