package com.flockware.coinadmin.ui.main.models

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.flockware.coinadmin.databinding.ViewMonthSelectorTitleBinding
import com.flockware.coinadmin.utils.DatePattern
import com.flockware.coinadmin.utils.pattern
import java.util.*

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class MonthModel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding = ViewMonthSelectorTitleBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp lateinit var date: Date

    @AfterPropsSet
    fun setupModel() {
        binding.vmstTitleTv.text = date.pattern(DatePattern.EXPLICIT_MONTH_YEAR)
    }
}