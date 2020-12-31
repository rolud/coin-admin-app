package com.flockware.coinadmin.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flockware.coinadmin.BuildConfig
import com.flockware.coinadmin.R
import com.flockware.coinadmin.databinding.ActivitySettingsBinding

class SettingsActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.asAppNameTv.text = resources.getString(R.string.app_name_version, BuildConfig.VERSION_NAME)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.apply {
            asTopBar.onLeftIconClick = { this@SettingsActivity.finish() }
            asCategoriesTv.setOnClickListener {  }
            asPinTv.setOnClickListener {
                val intent = Intent(this@SettingsActivity, SetPinActivity::class.java)
                this@SettingsActivity.startActivity(intent)
            }
            asFingerprintTv.setOnClickListener { asFingerprintSwitch.isChecked = asFingerprintSwitch.isChecked.not() }
        }
    }
}