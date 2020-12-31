package com.flockware.coinadmin.ui.settings

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ActivityPinBinding
import com.flockware.coinadmin.utils.ColorUtils
import org.koin.android.viewmodel.ext.android.getViewModel

class SetPinActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPinBinding
    private lateinit var viewModel: SetPinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        setKeyboard()
        observeData()

        viewModel.checkExistingPin()

    }

    private fun observeData() {
        viewModel.editMode.observe(this) { edit ->
            if (edit == true)
                setupEditMode()
            else
                setupCreateMode()
        }

        viewModel.digit1.observe(this) { digit ->
            val background = if (digit == null)
                R.drawable.background_ring
            else
                R.drawable.background_circle
            binding.apPinDigit1View.setBackgroundResource(background)
        }

        viewModel.digit2.observe(this) { digit ->
            val background = if (digit == null)
                R.drawable.background_ring
            else
                R.drawable.background_circle
            binding.apPinDigit2View.setBackgroundResource(background)
        }

        viewModel.digit3.observe(this) { digit ->
            val background = if (digit == null)
                R.drawable.background_ring
            else
                R.drawable.background_circle
            binding.apPinDigit3View.setBackgroundResource(background)
        }

        viewModel.digit4.observe(this) { digit ->
            val background = if (digit == null)
                R.drawable.background_ring
            else
                R.drawable.background_circle
            binding.apPinDigit4View.setBackgroundResource(background)
        }

        viewModel.pinSaved.observe(this) { saved ->
            if (saved == true) this.finish()
        }
    }

    private fun setKeyboard() {
        binding.apply {
            apPinKb0.setOnClickListener { viewModel.insertDigit(0) }
            apPinKb1.setOnClickListener { viewModel.insertDigit(1) }
            apPinKb2.setOnClickListener { viewModel.insertDigit(2) }
            apPinKb3.setOnClickListener { viewModel.insertDigit(3) }
            apPinKb4.setOnClickListener { viewModel.insertDigit(4) }
            apPinKb5.setOnClickListener { viewModel.insertDigit(5) }
            apPinKb6.setOnClickListener { viewModel.insertDigit(6) }
            apPinKb7.setOnClickListener { viewModel.insertDigit(7) }
            apPinKb8.setOnClickListener { viewModel.insertDigit(8) }
            apPinKb9.setOnClickListener { viewModel.insertDigit(9) }
            apPinKbDelete.setOnClickListener { viewModel.deleteDigit() }
        }
    }

    private fun setupEditMode() {
        binding.apply {
            apTitleTv.text = resources.getString(R.string.pin_insert_pin)
            apPinKbFingerprint.visibility = View.INVISIBLE
            apBackground.backgroundTintList = ColorStateList.valueOf(ColorUtils.NERO_INCUBO)
            viewModel.removeAllDigits()
        }
    }

    private fun setupCreateMode() {
        binding.apply {
            apTitleTv.text = resources.getString(R.string.pin_insert_new_pin)
            apPinKbFingerprint.visibility = View.INVISIBLE
            apBackground.backgroundTintList = ColorStateList.valueOf(ColorUtils.getColorPrimary(this@SetPinActivity))
            viewModel.removeAllDigits()
        }
    }


}