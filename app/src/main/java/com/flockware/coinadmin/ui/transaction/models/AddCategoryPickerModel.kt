package com.flockware.coinadmin.ui.transaction.models

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.flockware.coinadmin.databinding.ViewAddCategoryPickerModelBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class AddCategoryPickerModel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding = ViewAddCategoryPickerModelBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp(ModelProp.Option.DoNotHash) lateinit var onClick: () -> Unit

    @AfterPropsSet
    fun setupModel() {
        binding.vacpmLayout.setOnClickListener { onClick() }
    }

}