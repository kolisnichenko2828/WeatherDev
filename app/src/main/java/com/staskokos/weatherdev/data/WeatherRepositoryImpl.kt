package com.staskokos.weatherdev.data

import com.staskokos.weatherdev.domain.WeatherRepository
import com.staskokos.weatherdev.domain.models.Weather

class WeatherRepositoryImpl(private val weatherApi: WeatherApi) : WeatherRepository {
    private val apiKey: String = "01146fdd46a844f7a6793903230206"
    private val lang = "ru"
    private val aqi = "no"
    private val days = 3

    override suspend fun getWeather(q: String): Weather {
        val weatherRaw = weatherApi.getWeather(
            q = q,
            key = apiKey,
            lang = lang,
            days = days,
            aqi = aqi)
        val weather = weatherRaw.mapToWeather()

        return weather
    }
}