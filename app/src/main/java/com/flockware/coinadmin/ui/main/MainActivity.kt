package com.flockware.coinadmin.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ActivityMainBinding
import com.flockware.coinadmin.ui.dialogs.BottomSheetMenu
import com.flockware.coinadmin.ui.main.controllers.MonthsPickerController
import com.flockware.coinadmin.ui.main.controllers.TransactionsController
import com.flockware.coinadmin.ui.settings.SettingsActivity
import com.flockware.coinadmin.ui.transaction.AddTransactionActivity
import com.flockware.coinadmin.utils.*
import com.google.android.material.snackbar.Snackbar
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
    private var currentMonthPosition: Int = 0

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
            currentMonthPosition = pos
            viewModel.selectMonth(pos)
        }

        monthsController.addModelBuildListener {
            if (firstModelsBuild) {
                currentMonthPosition = 3
                binding.amToolbar.ctmaMonthRecyclerView.smoothScrollToPosition(currentMonthPosition)
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

        transactionsController.onOptionsClick = { transaction ->
            BottomSheetMenu.Builder(this)
                    .addMenuOption(resources.getString(R.string.edit), ColorUtils.getColorContent(this)) {
                        val intent = Intent(this, AddTransactionActivity::class.java)
                        intent.putExtra("edit_transaction_id", transaction.id)
                        this.startActivity(intent)
                    }
                    .addMenuOption(resources.getString(R.string.delete), ColorUtils.getColorContent(this)) { viewModel.deleteTransaction(transaction) }
                    .addMenuOption(resources.getString(R.string.close), ColorUtils.getColorContentSoft(this))
                    .build()
                    .show()
        }

    }

    private fun initClickListeners() {
        binding.apply {
            amToolbar.ctmaMenuIv.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                this@MainActivity.startActivity(intent)
            }
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
            viewModel.reloadTransactions()
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

        viewModel.snackbarMessage.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}