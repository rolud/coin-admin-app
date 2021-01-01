package com.flockware.coinadmin.ui.main

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ActivityPinBinding
import com.flockware.coinadmin.utils.ColorUtils
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.concurrent.Executor

class InsertPinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinBinding
    private lateinit var viewModel: InsertPinViewModel

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        setClickListeners()
        setUpBiometricAuthentication()
        observeData()

        viewModel.checkExistingPin()

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

        viewModel.startApp.observe(this) { canStart ->
            if (canStart == true) startApp()
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
                                            .withEndAction { startApp() }
                                            .start()
                                }
                                .start()
                    }
                    .start()

        }
    }

    private fun biometricAuthSucceeded() {
        binding.apPinDigit1View.setBackgroundResource(R.drawable.background_circle)
        Handler().postDelayed({ binding.apPinDigit2View.setBackgroundResource(R.drawable.background_circle) }, 120)
        Handler().postDelayed({ binding.apPinDigit3View.setBackgroundResource(R.drawable.background_circle) }, 240)
        Handler().postDelayed({ binding.apPinDigit4View.setBackgroundResource(R.drawable.background_circle) }, 360)
        Handler().postDelayed({ pinValid() }, 480)
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

    private fun startApp() {
        val intent = Intent(this@InsertPinActivity, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        this.startActivity(intent)
        this.finish()
    }

    private fun setUpBiometricAuthentication() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                biometricAuthSucceeded()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(resources.getString(R.string.log_in))
                .setNegativeButtonText(resources.getString(R.string.use_pin))
                .build()

        if (viewModel.isBiometricAuthEnabled) {
            binding.apPinKbFingerprint.visibility = View.VISIBLE
            binding.apPinKbFingerprint.setOnClickListener { showBiometricAuthentication() }
            showBiometricAuthentication()
        } else {
            binding.apPinKbFingerprint.visibility = View.INVISIBLE
        }
    }

    private fun showBiometricAuthentication() {
        biometricPrompt.authenticate(promptInfo)
    }



}