package br.com.fiap.airquality.ui.theme

data class ApiResponse(
    val status: String,
    val data: CityData
)

data class CityData(
    val city: String,
    val state: String,
    val country: String,
    val location: Location,
    val current: CurrentData
)

data class Location(
    val type: String,
    val coordinates: List<Double>
)

data class CurrentData(
    val pollution: Pollution,
    val weather: Weather
)

data class Pollution(
    val ts: String,
    val aqius: Int,
    val mainus: String,
    val aqicn: Int,
    val maincn: String
)

data class Weather(
    val ts: String,
    val tp: Int,
    val pr: Int,
    val hu: Int,
    val ws: Double,
    val wd: Int,
    val ic: String
)

