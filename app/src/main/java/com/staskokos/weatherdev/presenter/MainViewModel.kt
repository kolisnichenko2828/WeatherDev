package com.staskokos.weatherdev.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.staskokos.weatherdev.domain.models.Weather
import com.staskokos.weatherdev.domain.usecases.GetWeatherUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase
    ) : ViewModel() {
    val weatherLiveData = MutableLiveData<Weather>()

    fun getWeather(q: String) {
        viewModelScope.launch {
            val weather = getWeatherUsecase.execute(q)
            weatherLiveData.value = weather
        }
    }
}