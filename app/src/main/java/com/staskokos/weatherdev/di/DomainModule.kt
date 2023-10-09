package com.staskokos.weatherdev.di

import com.staskokos.weatherdev.domain.WeatherRepository
import com.staskokos.weatherdev.domain.usecases.GetWeatherUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DomainModule {

    @Provides
    fun provideGetWeatherUsecase(weatherRepository: WeatherRepository): GetWeatherUsecase {
        return GetWeatherUsecase(weatherRepository = weatherRepository)
    }
}