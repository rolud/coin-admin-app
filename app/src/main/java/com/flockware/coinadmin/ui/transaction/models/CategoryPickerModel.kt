package com.flockware.coinadmin.ui.transaction.models

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.databinding.ViewCategoryPickerModelBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CategoryPickerModel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding = ViewCategoryPickerModelBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp lateinit var category: Category
    @ModelProp(ModelProp.Option.DoNotHash) lateinit var onClick: () -> Unit

    @AfterPropsSet
    fun setupModel() {
        binding.apply {
            vcpmCategoryNameTv.text = category.name
            vcpmCategoryColorIv.imageTintList = ColorStateList.valueOf(Color.parseColor(category.color))
            vcpmLayout.setOnClickListener { onClick() }
        }
    }
}