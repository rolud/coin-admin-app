package com.flockware.coinadmin.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ActivityPinBinding
import com.flockware.coinadmin.ui.main.MainActivity
import com.flockware.coinadmin.utils.ColorUtils
import org.koin.android.viewmodel.ext.android.getViewModel

class InsertPinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinBinding
    private lateinit var viewModel: InsertPinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        setClickListeners()

        observeData()
    }

    private fun setClickListeners(){

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

    private fun observeData() {
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

        viewModel.pinValid.observe(this) { isValid ->
            if (isValid == true) {
                pinValid()
            } else if (isValid == false) {
                pinNotValid()
            }
        }
    }

    private fun pinValid() {
        binding.apply {
            apPinDigit1View.backgroundTintList = ColorStateList.valueOf(ColorUtils.BROCCOLO)
            apPinDigit2View.backgroundTintList = ColorStateList.valueOf(ColorUtils.BROCCOLO)
            apPinDigit3View.backgroundTintList = ColorStateList.valueOf(ColorUtils.BROCCOLO)
            apPinDigit4View.backgroundTintList = ColorStateList.valueOf(ColorUtils.BROCCOLO)

            apPinContainer.animate()
                    .setDuration(150)
                    .translationY(30f)
                    .withEndAction {
                        apPinContainer.animate()
                                .setDuration(150)
                                .translationY(-30f)
                                .withEndAction {
                                    apPinContainer.animate()
                                            .setDuration(150)
                                            .translationY(0f)
                                            .withEndAction {
                                                val intent = Intent(this@InsertPinActivity, MainActivity::class.java).apply {
                                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                }
                                                this@InsertPinActivity.startActivity(intent)
                                            }
                                            .start()
                                }
                                .start()
                    }
                    .start()

        }
    }

    private fun pinNotValid() {
        binding.apply {
            apPinDigit1View.backgroundTintList = ColorStateList.valueOf(ColorUtils.DARTH_MAUL)
            apPinDigit2View.backgroundTintList = ColorStateList.valueOf(ColorUtils.DARTH_MAUL)
            apPinDigit3View.backgroundTintList = ColorStateList.valueOf(ColorUtils.DARTH_MAUL)
            apPinDigit4View.backgroundTintList = ColorStateList.valueOf(ColorUtils.DARTH_MAUL)

            apPinContainer.animate()
                    .setDuration(150)
                    .translationX(50f)
                    .withEndAction {
                        apPinContainer.animate()
                                .setDuration(150)
                                .translationX(-50f)
                                .withEndAction {
                                    apPinContainer.animate()
                                            .setDuration(150)
                                            .translationX(0f)
                                            .withEndAction {
                                                apPinDigit1View.backgroundTintList = null
                                                apPinDigit2View.backgroundTintList = null
                                                apPinDigit3View.backgroundTintList = null
                                                apPinDigit4View.backgroundTintList = null

                                                viewModel.removeAllDigits()
                                            }
                                            .start()
                                }
                                .start()
                    }
                    .start()
        }
    }


}