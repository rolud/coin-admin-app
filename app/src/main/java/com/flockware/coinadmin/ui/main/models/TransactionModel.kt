package com.flockware.coinadmin.ui.main.models

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.flockware.coinadmin.R
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.databinding.ViewTransactionModelBinding
import com.flockware.coinadmin.utils.ColorUtils
import com.flockware.coinadmin.utils.DatePattern
import com.flockware.coinadmin.utils.pattern

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TransactionModel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding = ViewTransactionModelBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp lateinit var transaction: Transaction
    @ModelProp(ModelProp.Option.DoNotHash) lateinit var onOptionsClick: () -> Unit

    @AfterPropsSet
    fun setupModel() {
        binding.apply {

            val icon = when (transaction.paymentMethod) {
                Transaction.PaymentMethod.CASH -> R.drawable.ic_money
                Transaction.PaymentMethod.DIGITAL -> R.drawable.ic_credit_card
            }
            var color = when (transaction.type) {
                Transaction.TransactionType.PAID_IN -> ColorUtils.BROCCOLO
                Transaction.TransactionType.PAID_OUT -> ColorUtils.DARTH_MAUL
            }
            vtmPaymentMethodIv.setImageResource(icon)
            vtmPaymentMethodIv.imageTintList = ColorStateList.valueOf(color)

            vtmAmountTv.text = resources.getString(R.string.amount_euros, "%.2f".format(transaction.amount).replace(".", ","))
            vtmDescTv.text = transaction.desc
            vtmCategoryTv.text = transaction.category?.name ?: resources.getString(R.string.transaction_no_category)
            vtmDateTv.text = transaction.date.pattern(DatePattern.EXPLICIT_DAY_MONTH_YEAR)

            vtmDescTv.isSelected = true

            color =
                if (transaction.category?.color != null)
                    Color.parseColor(transaction.category!!.color!!)
                else
                    ColorUtils.getColorBackgroundLv1(context)
            vtmLayout.backgroundTintList = ColorStateList.valueOf(color)

            vtmOptionsIv.setOnClickListener { onOptionsClick() }
        }
    }
}