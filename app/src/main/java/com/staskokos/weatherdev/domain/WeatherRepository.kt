package com.staskokos.weatherdev.domain

import com.staskokos.weatherdev.domain.models.Weather

interface WeatherRepository {
    suspend fun getWeather(q: String): Weather
}