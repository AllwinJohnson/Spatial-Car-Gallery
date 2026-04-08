package org.example.project.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import org.example.project.domain.Supercar
import kotlin.random.Random

class CarState(val car: Supercar) {
    var position by mutableStateOf(Offset.Zero)
    var velocity = Offset.Zero
    var size = IntSize.Zero
}

@Composable
fun FloatingGallery(
    supercars: List<Supercar>,
    tiltVector: Offset,
    modifier: Modifier = Modifier
) {
    val carStates = remember(supercars) {
        supercars.map { CarState(it) }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()

        LaunchedEffect(widthPx, heightPx) {
            if (widthPx > 0 && heightPx > 0) {
                carStates.forEach { state ->
                    if (state.position == Offset.Zero) {
                        state.position = Offset(
                            x = Random.nextFloat() * (widthPx * 0.5f) + widthPx * 0.25f,
                            y = Random.nextFloat() * (heightPx * 0.5f) + heightPx * 0.25f
                        )
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            var lastTime = 0L
            while (true) {
                withFrameNanos { frameTimeNanos ->
                    if (lastTime == 0L) {
                        lastTime = frameTimeNanos
                        return@withFrameNanos
                    }
                    
                    val dt = (frameTimeNanos - lastTime) / 1_000_000_000f
                    lastTime = frameTimeNanos
                    
                    val bounceFactor = 0.6f
                    val friction = 0.98f

                    carStates.forEach { state ->
                        val ax = tiltVector.x * 2.5f
                        val ay = tiltVector.y * 2.5f
                        
                        state.velocity = Offset(
                            x = (state.velocity.x + ax * dt) * friction,
                            y = (state.velocity.y + ay * dt) * friction
                        )
                        
                        val newX = state.position.x + state.velocity.x * dt
                        val newY = state.position.y + state.velocity.y * dt
                        
                        var finalX = newX
                        var finalY = newY
                        
                        val maxX = widthPx - state.size.width
                        val maxY = heightPx - state.size.height
                        
                        if (finalX < 0f) {
                            finalX = 0f
                            state.velocity = state.velocity.copy(x = -state.velocity.x * bounceFactor)
                        } else if (maxX > 0f && finalX > maxX) {
                            finalX = maxX
                            state.velocity = state.velocity.copy(x = -state.velocity.x * bounceFactor)
                        }
                        
                        if (finalY < 0f) {
                            finalY = 0f
                            state.velocity = state.velocity.copy(y = -state.velocity.y * bounceFactor)
                        } else if (maxY > 0f && finalY > maxY) {
                            finalY = maxY
                            state.velocity = state.velocity.copy(y = -state.velocity.y * bounceFactor)
                        }
                        
                        state.position = Offset(finalX, finalY)
                    }
                }
            }
        }

        carStates.forEach { state ->
            FloatingCarCard(
                car = state.car,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = state.position.x
                        translationY = state.position.y
                    }
                    .onSizeChanged { 
                        state.size = it 
                    }
            )
        }
    }
}
