package org.example.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.Supercar
import org.example.project.domain.TiltData

@Composable
fun SupercarCarousel(
    supercars: List<Supercar>,
    tiltData: TiltData,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { supercars.size })

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SupercarDetailCard(
                    car = supercars[page],
                    tiltData = tiltData
                )
            }
        }
    }
}
