package com.example.seriesapp.model.data

import com.google.gson.annotations.SerializedName

/**
 * Classe de réponse principale de l'API.
 * Correspond à la structure JSON retournée par l'endpoint most-popular
 */
data class SeriesResponse(
    @SerializedName("total") val total: String,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("tv_shows") val tvShows: List<TvShow>
)

/**
 * Modèle représentant une série TV avec les informations de base d'une série.
 */
data class TvShow(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("network") val network: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("image_thumbnail_path") val imageUrl: String
)