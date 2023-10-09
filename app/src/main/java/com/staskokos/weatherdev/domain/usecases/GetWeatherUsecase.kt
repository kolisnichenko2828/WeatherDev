package com.staskokos.weatherdev.domain.usecases

import com.staskokos.weatherdev.domain.WeatherRepository
import com.staskokos.weatherdev.domain.models.Weather

class GetWeatherUsecase(val weatherRepository: WeatherRepository) {
    suspend fun execute(q: String): Weather {
        val weather = weatherRepository.getWeather(q = q)

        return weather
    }
}