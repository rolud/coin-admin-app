package com.flockware.coinadmin.ui.transaction

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flockware.coinadmin.R
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.databinding.ActivityAddTransactionBinding
import com.flockware.coinadmin.ui.category.AddCategoryActivity
import com.flockware.coinadmin.utils.ColorUtils
import com.flockware.coinadmin.utils.DatePattern
import com.flockware.coinadmin.utils.pattern
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var viewModel: AddTransactionViewModel
    private val calendar = Calendar.getInstance()
    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        binding.aatDateEt.setText(calendar.time.pattern(DatePattern.SLASH_YEAR))
        viewModel.transactionDate = calendar.time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        initTopBar()
        setClickListeners()

        observeData()
    }

    private fun observeData() {
        viewModel.showCategory.observe(this) {
            val picker = CategoriesPickerDialog(this, viewModel.categoriesList)
            picker.onAddCategory = {
                val intent = Intent(this@AddTransactionActivity, AddCategoryActivity::class.java)
                this@AddTransactionActivity.startActivity(intent)
            }
            picker.onSelectedCategory = { category ->
                viewModel.selectCategory(category)
                binding.aatCategoryEt.setText(category.name)
            }
            picker.show()
        }

        viewModel.transactionSaved.observe(this) { result ->
            when (result) {
                is AppResult.Success -> {
                    Snackbar.make(binding.root, "Movimento inserito", Snackbar.LENGTH_SHORT).show()
                    this.finish()
                }
                is AppResult.Error -> {
                    Snackbar.make(binding.root, "Error: ${result.message}", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initTopBar() {
        binding.aatTopBar.onLeftIconClick = { this.finish() }
        binding.aatTopBar.onRightIconClick = { saveTransaction() }
    }

    private fun setClickListeners() {

        binding.apply {
            aatCategoryEt.keyListener = null
            aatCategoryEt.isFocusable = false
            aatCategoryEt.setOnClickListener {
                viewModel.getCategories()
            }

            aatDateEt.keyListener = null
            aatDateEt.isFocusable = false
            aatDateEt.setOnClickListener {
                DatePickerDialog(
                    this@AddTransactionActivity,
                    dateListener,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                ).show()
            }

            aatBtnPaymentCash.setOnClickListener { selectPaymentMethod(Transaction.PaymentMethod.CASH) }
            aatBtnPaymentDigital.setOnClickListener { selectPaymentMethod(Transaction.PaymentMethod.DIGITAL) }
            aatBtnPaidIn.setOnClickListener { selectTransactionType(Transaction.TransactionType.PAID_IN) }
            aatBtnPaidOut.setOnClickListener { selectTransactionType(Transaction.TransactionType.PAID_OUT) }
        }
    }

    private fun selectPaymentMethod(method: Transaction.PaymentMethod) {
        when (method) {
            Transaction.PaymentMethod.CASH -> binding.apply {
                aatBtnPaymentDigital.setBackgroundResource(R.drawable.background_btn_stroke)
                aatBtnPaymentDigital.setTextColor(ColorUtils.getColorAccent(this@AddTransactionActivity))
                aatBtnPaymentCash.setBackgroundResource(R.drawable.background_btn_filled)
                aatBtnPaymentCash.setTextColor(ColorUtils.WHITE)
            }
            Transaction.PaymentMethod.DIGITAL -> binding.apply {
                aatBtnPaymentCash.setBackgroundResource(R.drawable.background_btn_stroke)
                aatBtnPaymentCash.setTextColor(ColorUtils.getColorAccent(this@AddTransactionActivity))
                aatBtnPaymentDigital.setBackgroundResource(R.drawable.background_btn_filled)
                aatBtnPaymentDigital.setTextColor(ColorUtils.WHITE)
            }
        }
        viewModel.paymentMethod = method
    }

    private fun selectTransactionType(type: Transaction.TransactionType) {
        when (type) {
            Transaction.TransactionType.PAID_IN -> binding.apply {
                aatBtnPaidOut.setBackgroundResource(R.drawable.background_btn_stroke_red)
                aatBtnPaidOut.setTextColor(ColorUtils.DARTH_MAUL)
                aatBtnPaidIn.setBackgroundResource(R.drawable.background_btn_filled_green)
                aatBtnPaidIn.setTextColor(ColorUtils.WHITE)
            }
            Transaction.TransactionType.PAID_OUT -> binding.apply {
                aatBtnPaidIn.setBackgroundResource(R.drawable.background_btn_stroke_green)
                aatBtnPaidIn.setTextColor(ColorUtils.BROCCOLO)
                aatBtnPaidOut.setBackgroundResource(R.drawable.background_btn_filled_red)
                aatBtnPaidOut.setTextColor(ColorUtils.WHITE)
            }
        }
        viewModel.transactionType = type
    }

    private fun saveTransaction() {
        viewModel.saveTransaction(
            binding.aatDescEt.text?.toString(),
            binding.aatAmountEt.text?.toString()?.toFloatOrNull()
        )
    }
}