# WeatherData

Мобильное приложение для Android, показывающее прогноз погоды с использованием API weatherapi.com.

## Описание

Приложение позволяет:
- Получать прогноз погоды на 3 дня
- Просматривать почасовой прогноз
- Определять текущее местоположение пользователя
- Искать прогноз погоды по названию города (на английском языке)

## Технологии

- Архитектура: MVVM
- Single Activity подход
- Volley для работы с сетью
- TabLayout для навигации
- Picasso для загрузки изображений
- Google Play Services Location для определения местоположения

## Требования

- Android SDK 21+
- Разрешение на доступ к местоположению для определения текущей геопозиции

## Установка

1. Склонируйте репозиторий
2. Откройте проект в Android Studio
3. Синхронизируйте Gradle
4. Запустите приложение на эмуляторе или реальном устройстве

## API

Приложение использует бесплатное API weatherapi.com для получения данных о погоде. 

Основные эндпоинты:
BASE_URL = "https://api.weatherapi.com/v1/forecast.json"

Файл readme.pptx в репозитории - для визуального ознакомления функционала приложения.
