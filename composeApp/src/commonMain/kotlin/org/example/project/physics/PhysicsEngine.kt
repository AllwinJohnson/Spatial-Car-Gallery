package org.example.project.physics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.example.project.domain.TiltData
import org.example.project.sensor.SensorDataProvider
import kotlin.math.PI

class PhysicsEngine(
    coroutineScope: CoroutineScope,
    private val sensorDataProvider: SensorDataProvider = SensorDataProvider()
) {
    private val _tiltDataFlow = MutableStateFlow(TiltData(0f, 0f))
    val tiltDataFlow: StateFlow<TiltData> = _tiltDataFlow.asStateFlow()

    init {
        sensorDataProvider.getTiltFlow()
            .onEach { rawData ->
                // Android getOrientation outputs in radians. We clamp to ~15 degrees max (UI constraint).
                val maxRad = 15f * (PI.toFloat() / 180f)
                
                val pitchClamped = rawData.pitch.coerceIn(-maxRad, maxRad)
                val rollClamped = rawData.roll.coerceIn(-maxRad, maxRad)
                
                // Convert to degrees for Compose graphicsLayer rotationX / rotationY
                val pitchDeg = pitchClamped * (180f / PI.toFloat())
                val rollDeg = rollClamped * (180f / PI.toFloat())

                // Simple exponential moving average smoothing to prevent jitter
                val current = _tiltDataFlow.value
                val smoothedPitch = current.pitch * 0.7f + pitchDeg * 0.3f
                val smoothedRoll = current.roll * 0.7f + rollDeg * 0.3f
                
                _tiltDataFlow.value = TiltData(smoothedPitch, smoothedRoll)
            }
            .launchIn(coroutineScope)
    }
}
