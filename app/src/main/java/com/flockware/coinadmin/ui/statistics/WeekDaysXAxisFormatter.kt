package com.flockware.coinadmin.ui.statistics

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class WeekDaysXAxisFormatter : ValueFormatter() {

    private val days = arrayOf("LUN", "MAR", "MER", "GIO", "VEN", "SAB", "DOM")

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return  if (value.toInt().toFloat() == value) days.getOrNull(value.toInt() - 1) ?: "" else ""
    }
}