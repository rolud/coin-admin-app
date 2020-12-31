package com.flockware.coinadmin.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorInt
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.BottomSheetDialogMenuBinding
import com.flockware.coinadmin.databinding.ViewBottomSheetMenuItemBinding
import com.flockware.coinadmin.utils.LoggerCompat
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetMenu private constructor(
        context: Context,
        private val options: List<Triple<String, Int, () -> Unit>>,
        private val cancelable : Boolean
) : BottomSheetDialog(context) {

    private lateinit var binding: BottomSheetDialogMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomSheetDialogMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)

        window!!.apply {
            setLayout(size.x, -1)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.7f)
            attributes.windowAnimations = R.style.BottomSheetAnimation
        }

        setCancelable(cancelable)

        LoggerCompat.log(options.size, "Dimens of option")
        options.forEach { option ->
            val optionView = ViewBottomSheetMenuItemBinding.inflate(layoutInflater)
            optionView.vbsmiTextTv.apply {
                text = option.first
                setTextColor(option.second)
                setOnClickListener {
                    option.third()
                    this@BottomSheetMenu.dismiss()
                }
                LoggerCompat.log(option.first, "Option")
            }
            binding.bsdmOptionsContainer.addView(optionView.root)
        }

        binding.bsdmBack.setOnClickListener { this.dismiss() }
    }

    data class Builder(
            val context: Context,
            var options: MutableList<Triple<String, Int, () -> Unit>> = mutableListOf(),
            var cancelable : Boolean = true
    ) {
        fun addMenuOption(name: String, @ColorInt color: Int, onClick: () -> Unit = {}) = apply { this.options.add(Triple(name, color, onClick)) }
        fun cancelable(value: Boolean) = apply { this.cancelable = value }

        fun build() = BottomSheetMenu(context, options, cancelable)
    }
}