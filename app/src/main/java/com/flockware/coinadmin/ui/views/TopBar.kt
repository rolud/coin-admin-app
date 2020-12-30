package com.flockware.coinadmin.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ViewTopBarBinding

class TopBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewTopBarBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String = ""
        set(value) {
            field = value
            binding.vtbTitleTv.text = value
        }
    @DrawableRes private var iconLeft: Int = R.drawable.ic_arrow_left
        set(value) {
            field = value
            binding.vtbLeftIconIv.setImageResource(value)
        }
    @DrawableRes private var iconRight: Int = 0
        set(value) {
            field = value
            binding.vtbRightIconIv.setImageResource(value)
        }

    var onLeftIconClick: () -> Unit = {}
    var onRightIconClick: () -> Unit = {}

    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.TopBar).apply {
                title = getString(R.styleable.TopBar_tb_title) ?: ""
                iconLeft = getResourceId(R.styleable.TopBar_tb_left_icon, R.drawable.ic_arrow_left)
                iconRight = getResourceId(R.styleable.TopBar_tb_right_icon, 0)
                recycle()
            }
        }

        binding.apply {
            vtbLeftIconIv.setOnClickListener { onLeftIconClick() }
            vtbRightIconIv.setOnClickListener { onRightIconClick() }
        }

    }

}