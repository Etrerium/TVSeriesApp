package com.example.seriesapp.model.repository

import com.example.seriesapp.model.data.SeriesResponse
import com.example.seriesapp.model.service.ApiService
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getMostPopularSeries(page: Int): Result<SeriesResponse> {
        return try {
            val response = apiService.getMostPopularSeries(page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}