package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.Supercar
import org.example.project.domain.TiltData

@Composable
fun SupercarDetailCard(
    car: Supercar,
    tiltData: TiltData,
    modifier: Modifier = Modifier
) {
    val parallaxMultiplier = 4f
    
    Card(
        modifier = modifier
            .fillMaxSize(0.85f)
            .shadow(elevation = 24.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x66FFFFFF))
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF2B2B36),
                            Color(0xFF15151A)
                        ),
                        radius = 800f
                    )
                )
                .padding(24.dp)
        ) {
            // Placeholder Car Graphic
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        rotationX = tiltData.pitch
                        rotationY = tiltData.roll
                        translationX = tiltData.roll * parallaxMultiplier
                        translationY = tiltData.pitch * parallaxMultiplier
                        cameraDistance = 8f * density
                    }
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(12.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF00FFCC), Color(0xFF0088AA))))
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "3D CAR GRAPHIC",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    letterSpacing = 2.sp
                )
            }

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
        }
    }
}
