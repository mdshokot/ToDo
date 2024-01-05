package com.shokot.todo.screen.main.components.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

data class Bar(val date: String, val value: Int)

@Composable
fun BarChart(bars: List<Bar>) {
    val maxValue = bars.maxByOrNull { it.value }?.value ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            drawBars(bars, maxValue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        bars.forEach { bar ->
            Text(
                text = "${bar.date}: ${bar.value}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun DrawScope.drawBars(bars: List<Bar>, maxValue: Int) {
    val barWidth = size.width / bars.size.toFloat()

    bars.forEachIndexed { index, bar ->
        val barHeight = (bar.value * size.height) / maxValue
        val rect = Rect(
            left = index * barWidth,
            top = size.height - barHeight,
            right = (index + 1) * barWidth,
            bottom = size.height
        )

        val brush = Brush.verticalGradient(
            colors = listOf(
                Color.Blue.copy(alpha = 0.7f),
                Color.Blue.copy(alpha = 1.0f)
            ),
            startY = rect.top,
            endY = rect.bottom
        )

    }
}