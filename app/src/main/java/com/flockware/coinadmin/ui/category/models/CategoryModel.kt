package com.flockware.coinadmin.ui.category.models

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
import com.flockware.coinadmin.databinding.ViewCategoryModelBinding
import com.flockware.coinadmin.utils.ColorUtils

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CategoryModel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStlyle: Int = 0
) : LinearLayout(context, attrs, defStlyle) {

    private val binding: ViewCategoryModelBinding = ViewCategoryModelBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp lateinit var category: Category
    @ModelProp(ModelProp.Option.DoNotHash) lateinit var onClick: () -> Unit

    @AfterPropsSet
    fun modelSetup() {
        binding.apply {
            vcmNameTv.text = category.name
            val color =
                if (category.color != null)
                    Color.parseColor(category.color)
                else
                    ColorUtils.getColorBackgroundLv1(context)
            vcmLayout.backgroundTintList = ColorStateList.valueOf(color)
            vcmLayout.setOnClickListener { onClick() }
        }
    }
}