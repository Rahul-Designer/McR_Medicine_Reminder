package com.example.mcrmedicinereminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mcrmedicinereminder.activity.HomeActivity
import com.example.mcrmedicinereminder.activity.SignIn
import com.example.mcrmedicinereminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        Handler(Looper.getMainLooper()).postDelayed({
            // Shared Preference Code
            val pref = getSharedPreferences("login", MODE_PRIVATE)
            val check = pref.getBoolean("flag", false)
            if (check) {
                // for true Login (User already login )
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }
            else{
                val intent = Intent(this, SignIn::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }
}