package com.flockware.coinadmin.ui.statistics

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.flockware.coinadmin.R
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.databinding.CardStatisticsBinding
import com.flockware.coinadmin.utils.ColorUtils
import com.flockware.coinadmin.utils.LoggerCompat
import com.flockware.coinadmin.utils.Poppins
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StatisticsCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = CardStatisticsBinding.inflate(LayoutInflater.from(context), this, true)

    private val paidInEntries: MutableList<PieEntry> = mutableListOf()
    private val paidOutEntries: MutableList<PieEntry> = mutableListOf()

    init {
        initCharts()
    }

    private fun initCharts() {
        binding.csChartPaidIn.isEnabled = false
        binding.csChartPaidIn.apply {
            description.isEnabled = false
            holeRadius = 65f
            legend.isEnabled = false
            centerText = "Entrate"
            setHoleColor(ColorUtils.getColorBackgroundLv1(context))
            setCenterTextColor(ColorUtils.BROCCOLO)
            setCenterTextTypeface(Poppins.light(context))
            setCenterTextSize(12f)
            setDrawEntryLabels(false)
            setUsePercentValues(false)

        }

        binding.csChartPaidOut.isEnabled = false
        binding.csChartPaidOut.apply {
            description.isEnabled = false
            holeRadius = 65f
            legend.isEnabled = false
            centerText = "Uscite"
            setHoleColor(ColorUtils.getColorBackgroundLv1(context))
            setCenterTextColor(ColorUtils.RED)
            setCenterTextTypeface(Poppins.light(context))
            setCenterTextSize(12f)
            setDrawEntryLabels(false)
            setUsePercentValues(false)
        }
    }

    fun loadTransactions(transactions: List<Transaction>) {
        val paidInColors: MutableList<Int> = mutableListOf()

        paidInEntries.clear()
        transactions.filter { it.type == Transaction.TransactionType.PAID_IN }
            .groupBy { it.category }
            .forEach { mapEntry ->
                val amount = mapEntry.value.sumOf { it.amount.toDouble() }.toFloat()
                paidInEntries.add( PieEntry(amount, mapEntry.key?.name ?: resources.getString(R.string.transaction_no_category)) )
                if (mapEntry.key?.color != null)
                    paidInColors.add(Color.parseColor(mapEntry.key!!.color!!))
                else
                    paidInColors.add(ColorUtils.getColorBackground(context))
            }

        if (paidInEntries.isEmpty()) {
            paidInEntries.add( PieEntry(1f, "nothing"))
            paidInColors.add(ColorUtils.GRAY_OVERLAY)
        }

        val paidInSet = PieDataSet(paidInEntries, resources.getString(R.string.transaction_paid_in))
        paidInSet.sliceSpace = 2f
        paidInSet.colors = paidInColors
        paidInSet.setDrawValues(false)
        val paidInData = PieData(paidInSet)
        binding.csChartPaidIn.data = paidInData
        binding.csChartPaidIn.invalidate()
        binding.csChartPaidIn.animateY(700)

        val paidOutColors: MutableList<Int> = mutableListOf()
        paidOutEntries.clear()
        transactions.filter { it.type == Transaction.TransactionType.PAID_OUT }
            .groupBy { it.category }
            .forEach { mapEntry ->
                val amount = mapEntry.value.sumOf { it.amount.toDouble() }.toFloat()
                paidOutEntries.add( PieEntry(amount, mapEntry.key?.name ?: resources.getString(R.string.transaction_no_category)) )
                if (mapEntry.key?.color != null)
                    paidOutColors.add(Color.parseColor(mapEntry.key!!.color!!))
                else
                    paidOutColors.add(ColorUtils.getColorBackground(context))
            }

        if (paidOutEntries.isEmpty()) {
            paidOutEntries.add( PieEntry(1f, "nothing"))
            paidOutColors.add(ColorUtils.GRAY_OVERLAY)
        }

        val paidOutSet = PieDataSet(paidOutEntries, resources.getString(R.string.transaction_paid_out))
        paidOutSet.sliceSpace = 2f
        paidOutSet.colors = paidOutColors
        paidOutSet.setDrawValues(false)
        val paidOutData = PieData(paidOutSet)
        binding.csChartPaidOut.data = paidOutData
        binding.csChartPaidOut.invalidate()
        binding.csChartPaidOut.animateY(700)
    }

}