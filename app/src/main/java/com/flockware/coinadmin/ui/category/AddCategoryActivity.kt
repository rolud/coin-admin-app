package com.flockware.coinadmin.ui.category

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flockware.coinadmin.R
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.databinding.ActivityAddCategoryBinding
import com.flockware.coinadmin.utils.ColorUtils
import com.flockware.coinadmin.utils.ColorUtils.getColorString
import com.google.android.material.snackbar.Snackbar
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import org.koin.android.viewmodel.ext.android.getViewModel

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryBinding
    private lateinit var viewModel: AddCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        initTopBar()
        initColorPicker()

        observeData()
    }


    private fun observeData() {
        viewModel.categorySaved.observe(this) { result ->
            when (result) {
                is AppResult.Success -> {
                    this.finish()
                }
                is AppResult.Error -> {
                    Snackbar.make(binding.root, "Error: ${result.message}", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun initTopBar() {
        binding.aacTopBar.onLeftIconClick = { this.finish() }
        binding.aacTopBar.onRightIconClick = { saveCategory() }
    }

    private fun initColorPicker() {
        binding.apply {
            aacColorPickerNavyIv.setOnClickListener {
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerNavyIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.NAVY.getColorString()
            }
            aacColorPickerBlueIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.BLUE.getColorString()
            }
            aacColorPickerAquaIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.AQUA.getColorString()
            }
            aacColorPickerTealIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.TEAL.getColorString()
            }
            aacColorPickerOliveIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.OLIVE.getColorString()
            }
            aacColorPickerGreenIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.GREEN.getColorString()
            }
            aacColorPickerLimeIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.LIME.getColorString()
            }
            aacColorPickerYellowIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.YELLOW.getColorString()
            }
            aacColorPickerOrangeIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.ORANGE.getColorString()
            }
            aacColorPickerRedIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.RED.getColorString()
            }
            aacColorPickerMaroonIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.MAROON.getColorString()
            }
            aacColorPickerFuchsiaIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.FUCHSIA.getColorString()
            }
            aacColorPickerPurpleIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(R.drawable.ic_check)
                viewModel.color = ColorUtils.PURPLE.getColorString()
            }
            aacColorPickerNoColorIv.setOnClickListener {
                aacColorPickerNavyIv.setImageResource(0)
                aacColorPickerBlueIv.setImageResource(0)
                aacColorPickerAquaIv.setImageResource(0)
                aacColorPickerTealIv.setImageResource(0)
                aacColorPickerOliveIv.setImageResource(0)
                aacColorPickerGreenIv.setImageResource(0)
                aacColorPickerLimeIv.setImageResource(0)
                aacColorPickerYellowIv.setImageResource(0)
                aacColorPickerOrangeIv.setImageResource(0)
                aacColorPickerRedIv.setImageResource(0)
                aacColorPickerMaroonIv.setImageResource(0)
                aacColorPickerFuchsiaIv.setImageResource(0)
                aacColorPickerPurpleIv.setImageResource(0)
                aacColorPickerCustomColorIv.setImageResource(0)
                aacColorPickerNoColorIv.setImageResource(R.drawable.ic_check)
                viewModel.color = null
            }
            aacColorPickerCustomColorIv.setOnClickListener {
                val builder = ColorPickerDialog.Builder(this@AddCategoryActivity)
                    .setTitle(resources.getString(R.string.select_color))
                    .attachAlphaSlideBar(false)
                    .setPositiveButton(
                        resources.getString(R.string.select),
                        object : ColorEnvelopeListener {
                            override fun onColorSelected(
                                envelope: ColorEnvelope?,
                                fromUser: Boolean
                            ) {
                                if (envelope != null) {
                                    aacColorPickerNavyIv.setImageResource(0)
                                    aacColorPickerBlueIv.setImageResource(0)
                                    aacColorPickerAquaIv.setImageResource(0)
                                    aacColorPickerTealIv.setImageResource(0)
                                    aacColorPickerOliveIv.setImageResource(0)
                                    aacColorPickerGreenIv.setImageResource(0)
                                    aacColorPickerLimeIv.setImageResource(0)
                                    aacColorPickerYellowIv.setImageResource(0)
                                    aacColorPickerOrangeIv.setImageResource(0)
                                    aacColorPickerRedIv.setImageResource(0)
                                    aacColorPickerMaroonIv.setImageResource(0)
                                    aacColorPickerFuchsiaIv.setImageResource(0)
                                    aacColorPickerPurpleIv.setImageResource(0)
                                    aacColorPickerNoColorIv.setImageResource(0)
                                    aacColorPickerCustomColorIv.setImageResource(R.drawable.ic_check)

                                    viewModel.color = "#${envelope.hexCode}"
                                }
                            }
                        })
                    .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }

                builder.colorPickerView.setInitialColor(Color.parseColor(viewModel.color ?: "#ffffffff"))
                builder.show()
            }
        }
    }

    private fun saveCategory() {
        viewModel.saveCategory(binding.aacNameEt.text?.toString())
    }
}