package org.example.project.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Supercar(
    val make: String,
    val model: String,
    val year: Int,
    @SerialName("0-60_time") val zeroToSixtyTime: String,
    val horsepower: Int,
    val torque: Int,
    val weight: Int,
    @SerialName("top_speed") val topSpeed: String,
    @SerialName("current_market_price") val currentMarketPrice: String,
    @SerialName("engine_details") val engineDetails: String,
    @SerialName("image_url") val imageUrl: String
)
