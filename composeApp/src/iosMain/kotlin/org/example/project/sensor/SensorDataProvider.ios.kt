package org.example.project.sensor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.example.project.domain.TiltData

actual class SensorDataProvider actual constructor() {
    actual fun getTiltFlow(): Flow<TiltData> {
        return flowOf(TiltData(0f, 0f))
    }
}
