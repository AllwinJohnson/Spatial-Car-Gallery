package org.example.project.physics

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.example.project.sensor.SensorDataProvider

class PhysicsEngine(
    coroutineScope: CoroutineScope,
    private val sensorDataProvider: SensorDataProvider = SensorDataProvider()
) {
    private val _offsetVector = MutableStateFlow(Offset.Zero)
    val offsetVector: StateFlow<Offset> = _offsetVector.asStateFlow()

    private val sensitivity = 500f

    init {
        sensorDataProvider.getTiltFlow()
            .onEach { tiltData ->
                val dx = tiltData.roll * sensitivity
                val dy = -tiltData.pitch * sensitivity
                _offsetVector.value = Offset(dx, dy)
            }
            .launchIn(coroutineScope)
    }
}
