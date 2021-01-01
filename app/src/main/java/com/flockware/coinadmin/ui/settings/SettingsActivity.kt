package com.flockware.coinadmin.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.flockware.coinadmin.BuildConfig
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ActivitySettingsBinding
import com.flockware.coinadmin.utils.LoggerCompat
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.concurrent.Executor

class SettingsActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val biometrichAuthSwitchOnCheckChangeListener =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) showBiometricAuthentication() else biometricAuthDisabled()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        binding.asAppNameTv.text = resources.getString(R.string.app_name_version, BuildConfig.VERSION_NAME)

        setClickListeners()
        setUpBiometricAuthentication()
        observeData()

        viewModel.checkPinSettings()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkPinSettings()
    }

    private fun setClickListeners() {
        binding.apply {
            asTopBar.onLeftIconClick = { this@SettingsActivity.finish() }
            asCategoriesTv.setOnClickListener {  }
            asPinTv.setOnClickListener {
                val intent = Intent(this@SettingsActivity, SetPinActivity::class.java)
                this@SettingsActivity.startActivity(intent)
            }
            asFingerprintTv.setOnClickListener {
                asFingerprintSwitch.isChecked = asFingerprintSwitch.isChecked.not()
            }
            asFingerprintSwitch.setOnCheckedChangeListener(biometrichAuthSwitchOnCheckChangeListener)
        }
    }

    private fun observeData() {
        viewModel.pinIsEnabled.observe(this) { isEnabled ->
            if (isEnabled == true) {
                binding.asPinTv.text = resources.getString(R.string.settings_edit_pin)
                binding.asFingerprintIv.isEnabled = true
                binding.asFingerprintTv.isEnabled = true
                binding.asFingerprintSwitch.isEnabled = true
                binding.asFingerprintIv.alpha = 1f
                binding.asFingerprintTv.alpha = 1f
                binding.asFingerprintSwitch.alpha = 1f
                if (binding.asFingerprintSwitch.isChecked != viewModel.isBiometricAuthEnabled) {
                    binding.asFingerprintSwitch.setOnCheckedChangeListener(null)
                    binding.asFingerprintSwitch.isChecked = viewModel.isBiometricAuthEnabled
                    binding.asFingerprintSwitch.setOnCheckedChangeListener(biometrichAuthSwitchOnCheckChangeListener)
                }
            } else {
                binding.asPinTv.text = resources.getString(R.string.settings_create_pin)
                binding.asFingerprintIv.isEnabled = false
                binding.asFingerprintTv.isEnabled = false
                binding.asFingerprintSwitch.isEnabled = false
                binding.asFingerprintIv.alpha = .3f
                binding.asFingerprintTv.alpha = .3f
                binding.asFingerprintSwitch.alpha = .3f
                binding.asFingerprintSwitch.setOnCheckedChangeListener(null)
                binding.asFingerprintSwitch.isChecked = false
                binding.asFingerprintSwitch.setOnCheckedChangeListener(biometrichAuthSwitchOnCheckChangeListener)
            }
        }
    }

    private fun setUpBiometricAuthentication() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                biometricAuthEnabled()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                biometricAuthDisabled()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                biometricAuthDisabled()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(resources.getString(R.string.authorizes))
            .setNegativeButtonText(resources.getString(R.string.cancel))
            .build()

    }

    private fun showBiometricAuthentication() {
        biometricPrompt.authenticate(promptInfo)
    }

    private fun biometricAuthEnabled() {
        viewModel.enableBiometricAuthentication(true)
        binding.asFingerprintSwitch.setOnCheckedChangeListener(null)
        binding.asFingerprintSwitch.isChecked = true
        binding.asFingerprintSwitch.setOnCheckedChangeListener(biometrichAuthSwitchOnCheckChangeListener)
    }

    private fun biometricAuthDisabled() {
        viewModel.enableBiometricAuthentication(false)
        binding.asFingerprintSwitch.setOnCheckedChangeListener(null)
        binding.asFingerprintSwitch.isChecked = false
        binding.asFingerprintSwitch.setOnCheckedChangeListener(biometrichAuthSwitchOnCheckChangeListener)
    }
}