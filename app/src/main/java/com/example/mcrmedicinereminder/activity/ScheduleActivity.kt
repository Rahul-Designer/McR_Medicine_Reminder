package com.example.mcrmedicinereminder.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mcrmedicinereminder.R
import com.example.mcrmedicinereminder.databinding.ActivityScheduleBinding

class ScheduleActivity : AppCompatActivity() {
    lateinit var binding: ActivityScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule)
    }
}