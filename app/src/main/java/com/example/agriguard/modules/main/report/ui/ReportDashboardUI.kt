package com.example.agriguard.modules.main.report.ui

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.agriguard.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun ReportDashboardUI(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var expanded by remember { mutableStateOf(false) }
        val listOfDate = listOf("January","February","March","April","May","June","July","August","September","October","November","December")
        var selectedDate by remember { mutableStateOf("Select Dates") }
        var isSampleFormDialogVisible by rememberSaveable { mutableStateOf(false) }

        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 4.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFF136204),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {}

        Row(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(85.dp)
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .background(Color(0xFF8dc63e))
                    )
                    Text(
                        text = "Damage",
                        fontSize = 16.sp,
                        color = Color(0xFF136204),
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .background(Color(0xFF136204))
                    )
                    Text(
                        text = "Undamage",
                        fontSize = 16.sp,
                        color = Color(0xFF136204),
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Analytical Report",
                        fontSize = 17.sp,
                        color = Color(0xFF136204),
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .width(130.dp)
                            .background(Color.Transparent)
                            .clickable { expanded = !expanded }
                            .wrapContentSize(Alignment.Center)
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val y = size.height - strokeWidth / 2
                                drawLine(
                                    color = Color(0xFF136204),
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = strokeWidth
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = selectedDate,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                fontSize = 16.sp,
                                color = Color(0xFF136204),
                                fontFamily = FontFamily.SansSerif,
                                textAlign = TextAlign.Center,
                            )
                            Icon(
                                painter = if (expanded) painterResource(id = R.drawable.ic_arrow_up) else painterResource(
                                    id = R.drawable.ic_arrow_down
                                ),
                                contentDescription = "Dropdown Icon",
                                tint = Color(0xFF136204),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(124.dp)
                                .background(color = Color.Transparent)
                                .wrapContentSize(Alignment.TopStart)
                                .heightIn(max = 165.dp)
                        ) {
                            listOfDate.forEach { dates ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = dates,
                                            color = Color(0xFF136204),
                                            fontFamily = FontFamily.SansSerif,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        )
                                    }, onClick = {
                                        selectedDate = dates
                                        expanded = false
                                    })
                            }
                        }
                    }
                }
            }
        }
        PieChart()
    }
}

val getPieChartData = listOf(
    PieChartData("Damage", 83.60F),
    PieChartData("Undamage", 16.40F),
)

data class PieChartData(var status: String?, var value: Float?)

@Composable
fun PieChart() {
    Column(
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(factory = { context ->
            PieChart(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                this.description.isEnabled = false

                this.isDrawHoleEnabled = false

                this.legend.isEnabled = false

                this.legend.textSize = 14F

                this.legend.horizontalAlignment =
                    Legend.LegendHorizontalAlignment.CENTER

                ContextCompat.getColor(context, R.color.white)
            }
        },
            modifier = Modifier
                .size(300.dp),
            update = {  pieChart ->
                updatePieChartWithData(pieChart, getPieChartData)
            }
        )
    }
}

fun updatePieChartWithData(
    chart: PieChart,
    data: List<PieChartData>
) {
    val entries = ArrayList<PieEntry>()

    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.status ?: ""))
    }

    val dataSet = PieDataSet(entries, "")
    val greenColor = Color(0xFF8dc63e)
    val lightGreenColor = Color(0xFF136204)

    dataSet.colors = arrayListOf(
        greenColor.toArgb(),
        lightGreenColor.toArgb()
    )
    dataSet.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    dataSet.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    dataSet.sliceSpace = 2f
    dataSet.valueTextSize = 18f
    dataSet.valueTypeface = Typeface.DEFAULT_BOLD

    val percentageTextColor = Color.White
    dataSet.valueTextColor = percentageTextColor.toArgb()
    val pieData = PieData(dataSet)

    chart.data = pieData
    chart.invalidate()
}