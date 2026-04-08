package org.example.project.data

import kotlinx.serialization.json.Json
import org.example.project.domain.Supercar
import org.jetbrains.compose.resources.ExperimentalResourceApi
import spatialcar.composeapp.generated.resources.Res

class SupercarRepository {
    private var cachedSupercars: List<Supercar>? = null

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getSupercars(): List<Supercar> {
        cachedSupercars?.let { return it }

        val bytes = Res.readBytes("files/supercars.json")
        val jsonString = bytes.decodeToString()
        val supercars = json.decodeFromString<List<Supercar>>(jsonString)
        
        cachedSupercars = supercars
        return supercars
    }
}
