package com.example.mcrmedicinereminder.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mcrmedicinereminder.R
import com.example.mcrmedicinereminder.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account)
    }
}