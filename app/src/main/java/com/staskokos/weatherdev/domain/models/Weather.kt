package com.staskokos.weatherdev.domain.models

import com.staskokos.weatherdev.data.models.Hour

data class Weather(
    val today: Today,
    val tomorrow: Tomorrow,
    val afterday: Afterday,
    val location: String
)

data class Today(
    val hours: List<Hour>
)
data class Tomorrow(
    val hours: List<Hour>
)
data class Afterday(
    val hours: List<Hour>
)