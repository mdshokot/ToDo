package com.shokot.todo.screen.main.components.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@Composable
fun CustomPiChart( completed:Int, notCompleted:Int) {
    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("", notCompleted.toFloat(), Color(0xFF5D615E)),
            PieChartData.Slice("", completed.toFloat(), Color(0xFF20BF55)),
        ),
        PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = false,
        animationDuration = 1500
    )

    PieChart(
        modifier = Modifier
            .width(400.dp)
            .height(400.dp)
            .background(MaterialTheme.colorScheme.background),
        donutChartData,
        pieChartConfig
    )
}