package com.flockware.coinadmin.ui.statistics

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import com.flockware.coinadmin.R
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.databinding.ActivityStatisticsBinding
import com.flockware.coinadmin.ui.category.AddCategoryActivity
import com.flockware.coinadmin.ui.main.controllers.MonthsPickerController
import com.flockware.coinadmin.ui.transaction.CategoriesPickerDialog
import com.flockware.coinadmin.utils.ColorUtils
import com.flockware.coinadmin.utils.Poppins
import com.flockware.coinadmin.utils.getDayOfTheMonth
import com.flockware.coinadmin.utils.getMonthDays
import com.github.mikephil.charting.data.*
import org.koin.android.viewmodel.ext.android.getViewModel
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager

class StatisticsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var viewModel: StatisticsViewModel
    private lateinit var periodsController: MonthsPickerController
    private lateinit var pickerLayoutManager: PickerLayoutManager

    private val snapHelper = LinearSnapHelper()

    private var firstModelsBuild = true
    private var currentPeriodPosition: Int = 0

    private val paidInPieEntries: MutableList<PieEntry> = mutableListOf()
    private val paidOutPieEntries: MutableList<PieEntry> = mutableListOf()

    private val paidInBarEntries: MutableList<BarEntry> = mutableListOf()
    private val paidOutBarEntries: MutableList<BarEntry> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        initTopBar()
        initCharts()
        initMonthController()
        setOnClickListeners()

        observeData()

        viewModel.initMonthlyView()

    }

    private fun initTopBar() {
        binding.apply {
            asTopBar.onLeftIconClick = { this@StatisticsActivity.finish() }
            asTopBar.onRightIconClick = {}
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            asCategoryPicker.setOnClickListener { viewModel.getCategories() }
        }
    }

    private fun initMonthController() {
        periodsController = MonthsPickerController(viewModel.periodsList)
        pickerLayoutManager = PickerLayoutManager(this@StatisticsActivity, PickerLayoutManager.HORIZONTAL, false).apply {
            isChangeAlpha = true
            scaleDownBy = .3f
            scaleDownDistance = .9f
        }

        binding.asPeriodSelectorRecyclerView.apply {
            layoutManager = pickerLayoutManager
            setHasFixedSize(false)
            adapter = periodsController.adapter
            isNestedScrollingEnabled = false
        }
        snapHelper.attachToRecyclerView(binding.asPeriodSelectorRecyclerView)
        pickerLayoutManager.setOnScrollStopListener {
            val pos = binding.asPeriodSelectorRecyclerView.getChildAdapterPosition(it)
            currentPeriodPosition = pos
            viewModel.selectMonth(pos)
        }

        periodsController.addModelBuildListener {
            if (firstModelsBuild) {
                currentPeriodPosition = 3
                binding.asPeriodSelectorRecyclerView.smoothScrollToPosition(currentPeriodPosition)
                firstModelsBuild = false
            }
        }
    }

    private fun initCharts() {
        binding.asChartPaidIn.apply {
            description.isEnabled = false
            holeRadius = 65f
            legend.isEnabled = false
            centerText = "Entrate"
            setHoleColor(ColorUtils.getColorBackground(context))
            setCenterTextColor(ColorUtils.BROCCOLO)
            setCenterTextTypeface(Poppins.light(context))
            setCenterTextSize(12f)
            setDrawEntryLabels(false)
            setUsePercentValues(false)

        }

        binding.asChartPaidOut.apply {
            description.isEnabled = false
            holeRadius = 65f
            legend.isEnabled = false
            centerText = "Uscite"
            setHoleColor(ColorUtils.getColorBackground(context))
            setCenterTextColor(ColorUtils.RED)
            setCenterTextTypeface(Poppins.light(context))
            setCenterTextSize(12f)
            setDrawEntryLabels(false)
            setUsePercentValues(false)
        }

        binding.asDailyBarChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
        }
    }


    private fun observeData() {
        viewModel.periodsListUpdated.observe(this) { updated ->
            if (updated == true)
                periodsController.requestModelBuild()
        }
        viewModel.transactionsListUpdated.observe(this) { updated ->
            if (updated == true) {
                updatePieCharts(viewModel.transactionsList)
                updateStandings(viewModel.transactionsList)
                updateDailyBarCharts(viewModel.transactionsList)
            }
        }
        viewModel.showCategory.observe(this) { showCategory ->
            if (showCategory == true) {
                val picker = CategoriesPickerDialog(this, viewModel.categoriesList, true)
                picker.onAddCategory = {
                    val intent = Intent(this@StatisticsActivity, AddCategoryActivity::class.java)
                    this@StatisticsActivity.startActivity(intent)
                }
                picker.onSelectedCategory = { category ->
                    viewModel.selectCategory(category)
                    binding.asCategoryPicker.text = category.name
                }
                picker.onSelectedNullCategory = {
                    viewModel.selectCategory(null)
                    binding.asCategoryPicker.text = "Senza categoria"
                }
                picker.onSelectedGeneral = {
                    viewModel.unselectCategory()
                    binding.asCategoryPicker.text = "Generale"
                }
                picker.show()
            }

        }
    }

    private fun updatePieCharts(transactions: List<Transaction>) {
        val paidInColors: MutableList<Int> = mutableListOf()

        paidInPieEntries.clear()
        transactions.filter { it.type == Transaction.TransactionType.PAID_IN }
            .groupBy { it.category }
            .forEach { mapEntry ->
                val amount = mapEntry.value.sumOf { it.amount.toDouble() }.toFloat()
                paidInPieEntries.add( PieEntry(amount, mapEntry.key?.name ?: resources.getString(R.string.transaction_no_category)) )
                if (mapEntry.key?.color != null)
                    paidInColors.add(Color.parseColor(mapEntry.key!!.color!!))
                else
                    paidInColors.add(ColorUtils.getColorBackground(this))
            }

        if (paidInPieEntries.isEmpty()) {
            paidInPieEntries.add( PieEntry(1f, "nothing"))
            paidInColors.add(ColorUtils.GRAY_OVERLAY)
        }

        val paidInSet = PieDataSet(paidInPieEntries, resources.getString(R.string.transaction_paid_in))
        paidInSet.sliceSpace = 2f
        paidInSet.colors = paidInColors
        paidInSet.setDrawValues(false)
        val paidInData = PieData(paidInSet)
        binding.asChartPaidIn.data = paidInData
        binding.asChartPaidIn.invalidate()
        binding.asChartPaidIn.animateY(700)

        val paidOutColors: MutableList<Int> = mutableListOf()
        paidOutPieEntries.clear()
        transactions.filter { it.type == Transaction.TransactionType.PAID_OUT }
            .groupBy { it.category }
            .forEach { mapEntry ->
                val amount = mapEntry.value.sumOf { it.amount.toDouble() }.toFloat()
                paidOutPieEntries.add( PieEntry(amount, mapEntry.key?.name ?: resources.getString(R.string.transaction_no_category)) )
                if (mapEntry.key?.color != null)
                    paidOutColors.add(Color.parseColor(mapEntry.key!!.color!!))
                else
                    paidOutColors.add(ColorUtils.getColorBackground(this))
            }

        if (paidOutPieEntries.isEmpty()) {
            paidOutPieEntries.add( PieEntry(1f, "nothing"))
            paidOutColors.add(ColorUtils.GRAY_OVERLAY)
        }

        val paidOutSet = PieDataSet(paidOutPieEntries, resources.getString(R.string.transaction_paid_out))
        paidOutSet.sliceSpace = 2f
        paidOutSet.colors = paidOutColors
        paidOutSet.setDrawValues(false)
        val paidOutData = PieData(paidOutSet)
        binding.asChartPaidOut.data = paidOutData
        binding.asChartPaidOut.invalidate()
        binding.asChartPaidOut.animateY(700)
    }

    private fun updateStandings(transactions: List<Transaction>) {
        val categoriesPaidIns: MutableMap<String, Float> = mutableMapOf()
        transactions.filter { it.type == Transaction.TransactionType.PAID_IN }.forEach { transaction ->
            val category = transaction.category?.name ?: "Senza categoria"
            categoriesPaidIns[category] = (categoriesPaidIns[category] ?: 0f) + transaction.amount
        }
        val categoriesPaidInStanding = categoriesPaidIns.map { Pair(it.key, it.value) }.sortedByDescending { it.second }
        binding.asPaidInCategoryStandingPos1.text =
            if (categoriesPaidInStanding.isNotEmpty())
                resources.getString(R.string.standing_position, 1, categoriesPaidInStanding[0].first)
            else
                resources.getString(R.string.standing_position_null, 1)
        binding.asPaidInCategoryStandingPos2.text =
            if (categoriesPaidInStanding.size > 1)
                resources.getString(R.string.standing_position, 2, categoriesPaidInStanding[1].first)
            else
                resources.getString(R.string.standing_position_null, 2)
        binding.asPaidInCategoryStandingPos3.text =
            if (categoriesPaidInStanding.size > 2)
                resources.getString(R.string.standing_position, 3, categoriesPaidInStanding[2].first)
            else
                resources.getString(R.string.standing_position_null, 3)

        val categoriesPaidOuts: MutableMap<String, Float> = mutableMapOf()
        transactions.filter { it.type == Transaction.TransactionType.PAID_OUT }.forEach { transaction ->
            val category = transaction.category?.name ?: "Senza categoria"
            categoriesPaidOuts[category] = (categoriesPaidOuts[category] ?: 0f) + transaction.amount
        }
        val categoriesPaidOutsStanding = categoriesPaidOuts.map { Pair(it.key, it.value) }.sortedByDescending { it.second }
        binding.asPaidOutCategoryStandingPos1.text =
            if (categoriesPaidOutsStanding.isNotEmpty())
                resources.getString(R.string.standing_position, 1, categoriesPaidOutsStanding[0].first)
            else
                resources.getString(R.string.standing_position_null, 1)
        binding.asPaidOutCategoryStandingPos2.text =
            if (categoriesPaidOutsStanding.size > 1)
                resources.getString(R.string.standing_position, 2, categoriesPaidOutsStanding[1].first)
            else
                resources.getString(R.string.standing_position_null, 2)
        binding.asPaidOutCategoryStandingPos3.text =
            if (categoriesPaidOutsStanding.size > 2)
                resources.getString(R.string.standing_position, 3, categoriesPaidOutsStanding[2].first)
            else
                resources.getString(R.string.standing_position_null, 3)


    }

    private fun updateDailyBarCharts(transactions: List<Transaction>) {
        val paidInsDaily: MutableMap<Int, Float> = mutableMapOf()
        for (d in 1 .. viewModel.currentPeriod!!.getMonthDays())
            paidInsDaily[d] = 0f
        transactions.filter { it.type == Transaction.TransactionType.PAID_IN }.forEach { transaction ->
            val dom = transaction.date.getDayOfTheMonth()
            paidInsDaily[dom] = (paidInsDaily[dom] ?: 0f) + transaction.amount
        }

        val paidOutsDaily: MutableMap<Int, Float> = mutableMapOf()
        for (d in 1 .. viewModel.currentPeriod!!.getMonthDays())
            paidOutsDaily[d] = 0f
        transactions.filter { it.type == Transaction.TransactionType.PAID_OUT }.forEach { transaction ->
            val dom = transaction.date.getDayOfTheMonth()
            paidOutsDaily[dom] = (paidOutsDaily[dom] ?: 0f) + transaction.amount
        }

        paidInBarEntries.clear()
        paidOutBarEntries.clear()

        paidInsDaily.forEach { pidEntry ->
            paidInBarEntries.add(BarEntry(pidEntry.key.toFloat(), pidEntry.value))
        }
        paidOutsDaily.forEach { podEntry ->
            paidOutBarEntries.add(BarEntry(podEntry.key.toFloat(), podEntry.value))
        }

        val pidDataSet = BarDataSet(paidInBarEntries, "Entrate")
        pidDataSet.color = ColorUtils.BROCCOLO
        val podDataSet = BarDataSet(paidOutBarEntries, "Uscite")
        podDataSet.color = ColorUtils.DARTH_MAUL

        val groupSpace = .06f
        val barSpace = .02f
        val barWidth = .45f

        val data = BarData(pidDataSet, podDataSet)
        data.barWidth = barWidth

        binding.asDailyBarChart.data = data
        binding.asDailyBarChart.groupBars(1f, groupSpace, barSpace)
        binding.asDailyBarChart.invalidate()
    }
}