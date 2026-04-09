package org.example.project.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.kmpalette.PaletteResult
import com.kmpalette.extensions.painter.rememberPainterPaletteState
import com.kmpalette.palette.graphics.Palette
import org.example.project.domain.Supercar
import org.example.project.domain.TiltData

@Composable
fun SupercarDetailCard(
    car: Supercar,
    tiltData: TiltData,
    modifier: Modifier = Modifier
) {
    val parallaxMultiplier = 4f
    
    // 1. Reliable State Management with Animation
    var dominantColor by remember { mutableStateOf(Color(0xFF2B2B36)) }
    val animatedColor by animateColorAsState(
        targetValue = dominantColor,
        animationSpec = tween(durationMillis = 800),
        label = "colorFade"
    )
    
    // 2. KMPalette Painter State
    // We use rememberPainterPaletteState() which handles platform-specific bitmap conversion for us
    val paletteState = rememberPainterPaletteState()
    val painter = rememberAsyncImagePainter(model = car.imageUrl)
    val painterState by painter.state.collectAsState()
    
    // Trigger palette generation when Coil successfully loads the image
    LaunchedEffect(painterState) {
        val currentState = painterState
        if (currentState is AsyncImagePainter.State.Success) {
            paletteState.generate(currentState.painter)
        }
    }

    // Update dominantColor when palette generation succeeds
    LaunchedEffect(paletteState.state) {
        val result = paletteState.state
        if (result is PaletteResult.Success) {
            val palette = result.palette
            // Prefer vibrant swatch for a better glow effect, otherwise use dominant
            val swatch = palette.vibrantSwatch ?: palette.dominantSwatch ?: palette.swatches.maxByOrNull { it.population }
            swatch?.rgb?.let { rgb ->
                dominantColor = Color(rgb).copy(alpha = 1.0f) 
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxSize(0.85f)
            .shadow(elevation = 24.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x66FFFFFF))
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        // Root Container with the Animated Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to animatedColor,
                        0.6f to Color(0xFF1E1E29), // Make the base color take over starting from the middle
                        1.0f to Color(0xFF1E1E29)
                    )
                )
                .padding(24.dp)
        ) {
            // Content Layer (Texts)
            Column(
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = car.make.uppercase(),
                    color = Color(0xFFAAAAAA),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = car.model,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 36.sp
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("0-60 mph", color = Color(0xFF888888), fontSize = 12.sp)
                        Text(car.zeroToSixtyTime, color = Color(0xFF00FFCC), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Price", color = Color(0xFF888888), fontSize = 12.sp)
                        Text(car.currentMarketPrice, color = Color(0xFFFFCC00), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // 3. Render the Image within the 3D Parallax Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        rotationX = tiltData.pitch
                        rotationY = tiltData.roll
                        translationX = tiltData.roll * parallaxMultiplier
                        translationY = tiltData.pitch * parallaxMultiplier
                        cameraDistance = 8f * density
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painter,
                    contentDescription = car.model,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                
                // Show a subtle loading indicator if not success yet
                if (painterState is AsyncImagePainter.State.Loading || paletteState.state is PaletteResult.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
