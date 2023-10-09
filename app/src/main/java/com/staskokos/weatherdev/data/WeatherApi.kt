package com.staskokos.weatherdev.data

import com.staskokos.weatherdev.data.models.WeatherRaw
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json?")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("aqi") aqi: String,
        @Query("lang") lang: String,
        @Query("days") days: Int): WeatherRaw
}
