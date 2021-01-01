package com.flockware.coinadmin.ui.category

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flockware.coinadmin.R
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Category
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

        if (intent.hasExtra("edit_category_id")) {
            val categoryId = intent.getLongExtra("edit_category_id", 0L)
            viewModel.loadCategoryForEdit(categoryId)
        }
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

        viewModel.categoryForEdit.observe(this) { category ->
            if (category != null)
                loadCategory(category)
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

    private fun loadCategory(category: Category) {
        binding.aacNameEt.setText(category.name)
        binding.aacColorPickerNavyIv.setImageResource(0)
        binding.aacColorPickerBlueIv.setImageResource(0)
        binding.aacColorPickerAquaIv.setImageResource(0)
        binding.aacColorPickerTealIv.setImageResource(0)
        binding.aacColorPickerOliveIv.setImageResource(0)
        binding.aacColorPickerGreenIv.setImageResource(0)
        binding.aacColorPickerLimeIv.setImageResource(0)
        binding.aacColorPickerYellowIv.setImageResource(0)
        binding.aacColorPickerOrangeIv.setImageResource(0)
        binding.aacColorPickerRedIv.setImageResource(0)
        binding.aacColorPickerMaroonIv.setImageResource(0)
        binding.aacColorPickerFuchsiaIv.setImageResource(0)
        binding.aacColorPickerPurpleIv.setImageResource(0)
        binding.aacColorPickerNoColorIv.setImageResource(0)
        binding.aacColorPickerCustomColorIv.setImageResource(0)

        when (category.color) {
            ColorUtils.NAVY.getColorString()    -> binding.aacColorPickerNavyIv.setImageResource(R.drawable.ic_check)
            ColorUtils.BLUE.getColorString()    -> binding.aacColorPickerBlueIv.setImageResource(R.drawable.ic_check)
            ColorUtils.AQUA.getColorString()    -> binding.aacColorPickerAquaIv.setImageResource(R.drawable.ic_check)
            ColorUtils.TEAL.getColorString()    -> binding.aacColorPickerTealIv.setImageResource(R.drawable.ic_check)
            ColorUtils.OLIVE.getColorString()   -> binding.aacColorPickerOliveIv.setImageResource(R.drawable.ic_check)
            ColorUtils.GREEN.getColorString()   -> binding.aacColorPickerGreenIv.setImageResource(R.drawable.ic_check)
            ColorUtils.LIME.getColorString()    -> binding.aacColorPickerLimeIv.setImageResource(R.drawable.ic_check)
            ColorUtils.YELLOW.getColorString()  -> binding.aacColorPickerYellowIv.setImageResource(R.drawable.ic_check)
            ColorUtils.ORANGE.getColorString()  -> binding.aacColorPickerOrangeIv.setImageResource(R.drawable.ic_check)
            ColorUtils.RED.getColorString()     -> binding.aacColorPickerRedIv.setImageResource(R.drawable.ic_check)
            ColorUtils.MAROON.getColorString()  -> binding.aacColorPickerMaroonIv.setImageResource(R.drawable.ic_check)
            ColorUtils.FUCHSIA.getColorString() -> binding.aacColorPickerFuchsiaIv.setImageResource(R.drawable.ic_check)
            ColorUtils.PURPLE.getColorString()  -> binding.aacColorPickerPurpleIv.setImageResource(R.drawable.ic_check)
            null                                -> binding.aacColorPickerNoColorIv.setImageResource(R.drawable.ic_check)
            else                                -> binding.aacColorPickerCustomColorIv.setImageResource(R.drawable.ic_check)

        }
    }

    private fun saveCategory() {
        viewModel.saveCategory(binding.aacNameEt.text?.toString())
    }
}