package com.example.seriesapp.model.service

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.seriesapp.model.data.SeriesResponse

interface ApiService {
    @GET("most-popular")
    suspend fun getMostPopularSeries(@Query("page") page: Int): SeriesResponse
}