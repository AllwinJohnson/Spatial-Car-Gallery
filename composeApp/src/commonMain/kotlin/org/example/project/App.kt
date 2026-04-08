package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.data.SupercarRepository
import org.example.project.domain.Supercar
import org.example.project.domain.TiltData
import org.example.project.physics.PhysicsEngine
import org.example.project.ui.SupercarCarousel

@Composable
fun App() {
    MaterialTheme {
        val coroutineScope = rememberCoroutineScope()
        
        val repository = remember { SupercarRepository() }
        val physicsEngine = remember { PhysicsEngine(coroutineScope) }
        
        var supercars by remember { mutableStateOf<List<Supercar>?>(null) }
        val tiltData by physicsEngine.tiltDataFlow.collectAsState()

        LaunchedEffect(Unit) {
            supercars = repository.getSupercars()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0E)),
            contentAlignment = Alignment.Center
        ) {
            val cars = supercars
            if (cars == null) {
                CircularProgressIndicator(color = Color(0xFF00FFCC))
            } else {
                SupercarCarousel(
                    supercars = cars,
                    tiltData = tiltData
                )
            }
        }
    }
}