package org.example.project.sensor

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.TiltData

expect class SensorDataProvider() {
    fun getTiltFlow(): Flow<TiltData>
}
