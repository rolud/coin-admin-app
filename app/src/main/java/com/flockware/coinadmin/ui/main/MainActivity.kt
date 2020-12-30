package com.flockware.coinadmin.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ActivityMainBinding
import com.flockware.coinadmin.ui.main.controllers.MonthsPickerController
import com.flockware.coinadmin.ui.main.controllers.TransactionsController
import com.flockware.coinadmin.ui.transaction.AddTransactionActivity
import com.flockware.coinadmin.utils.*
import org.koin.android.viewmodel.ext.android.getViewModel
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var transactionsController: TransactionsController
    private lateinit var monthsController: MonthsPickerController
    private lateinit var pickerLayoutManager: PickerLayoutManager

    private val snapHelper = LinearSnapHelper()

    private var firstModelsBuild = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()


        initMonthController()
        initTransactionsController()
        initClickListeners()

        observeData()

        viewModel.initMonthsList()
    }


    private fun initMonthController() {
        monthsController = MonthsPickerController(viewModel.monthsList)
        pickerLayoutManager = PickerLayoutManager(this@MainActivity, PickerLayoutManager.HORIZONTAL, false).apply {
            isChangeAlpha = true
            scaleDownBy = .3f
            scaleDownDistance = .9f
        }

        binding.amToolbar.ctmaMonthRecyclerView.apply {
            layoutManager = pickerLayoutManager
            setHasFixedSize(false)
            adapter = monthsController.adapter
            isNestedScrollingEnabled = false
        }
        snapHelper.attachToRecyclerView(binding.amToolbar.ctmaMonthRecyclerView)
        pickerLayoutManager.setOnScrollStopListener {
            val pos = binding.amToolbar.ctmaMonthRecyclerView.getChildAdapterPosition(it)
            viewModel.selectMonth(pos)
        }

        monthsController.addModelBuildListener {
            if (firstModelsBuild) {
                binding.amToolbar.ctmaMonthRecyclerView.smoothScrollToPosition(3)
                firstModelsBuild = false
            }
        }
    }

    private fun initTransactionsController() {
        transactionsController = TransactionsController(viewModel.transactionsList)

        binding.amTransactionsRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
            adapter = transactionsController.adapter
        }

    }

    private fun initClickListeners() {
        binding.apply {
            amFab.setOnClickListener {
                val intent = Intent(this@MainActivity, AddTransactionActivity::class.java)
                this@MainActivity.startActivity(intent)
            }
        }
    }

    private fun observeData() {
        viewModel.monthsListUpdated.observe(this) { updated ->
            if (updated == true)
                monthsController.requestModelBuild()
        }

        viewModel.transactionsListUpdated.observe(this) { updated ->
            if (updated == true)
                transactionsController.requestModelBuild()
        }

        viewModel.transactionsLive.observe(this) {
            viewModel.reloadTransictions()
        }

        viewModel.paidInTotal.observe(this) { value ->
            binding.amToolbar.ctmaPaidInTv.text =
                    if (value != null && value != 0f)
                        "+" + resources.getString(R.string.amount_euros, "%.2f".format(value).replace(".", ","))
                    else " - "
        }

        viewModel.paidOutTotal.observe(this) { value ->
            binding.amToolbar.ctmaPaidOutTv.text =
                    if (value != null && value != 0f)
                        resources.getString(R.string.amount_euros, "%.2f".format(value).replace(".", ","))
                    else " - "
        }

        viewModel.paidDifferenceTotal.observe(this) { value ->
            val color = when {
                value == null || value == 0f -> ColorUtils.WHITE
                value > 0f -> ColorUtils.BROCCOLO
                else -> ColorUtils.DARTH_MAUL
            }
            binding.amToolbar.ctmaPaidDifferenceTv.setTextColor(color)
            binding.amToolbar.ctmaPaidDifferenceTv.text =
                    when {
                        (value == null || value == 0f) -> " - "
                        value > 0f -> "+" + resources.getString(R.string.amount_euros, "%.2f".format(value).replace(".", ","))
                        else -> resources.getString(R.string.amount_euros, "%.2f".format(value).replace(".", ","))
                    }
        }
    }
}