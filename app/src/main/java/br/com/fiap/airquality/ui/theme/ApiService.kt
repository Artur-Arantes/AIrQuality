package br.com.fiap.airquality.ui.theme

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/city")
    suspend fun getCityData(
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("country") country: String = "Brazil",
        @Query("key") key: String = "739a26eb-0228-4d04-b840-47fa2afbe946"
    ): ApiResponse
}

