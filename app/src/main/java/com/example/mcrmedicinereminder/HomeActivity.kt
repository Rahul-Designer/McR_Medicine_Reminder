package com.example.mcrmedicinereminder

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mcrmedicinereminder.databinding.ActivityHomeBinding
import com.example.mcrmedicinereminder.fragment.DashboardFragment
import com.example.mcrmedicinereminder.fragment.ReminderFragment
import com.example.mcrmedicinereminder.fragment.SellFragment
import com.example.mcrmedicinereminder.fragment.TipFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private var selectedTab = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        // set default fragment
        replaceFragment(ReminderFragment())


        binding.reminderLayout.setOnClickListener {
            if (selectedTab != 1) {

                // change fragment
                replaceFragment(ReminderFragment())

                binding.reminderText.visibility = View.VISIBLE
                binding.sellLayout.background = null
                binding.tipLayout.background = null
                binding.dashboardLayout.background = null
                binding.reminderLayout.setBackgroundResource(R.drawable.nav_round_bg)


                // set other nav item text visibility

                binding.sellText.visibility = View.GONE
                binding.tipText.visibility = View.GONE
                binding.dashboardText.visibility = View.GONE

                // set animation
                val scaleAnimation = ScaleAnimation(
                    0.8f,
                    1.0f,
                    1f,
                    1f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f
                )
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                binding.reminderLayout.startAnimation(scaleAnimation)

                selectedTab = 1
            }

        }

        binding.sellLayout.setOnClickListener {
            if (selectedTab != 2) {
                // change fragment
                replaceFragment(SellFragment())
                binding.sellText.visibility = View.VISIBLE
                binding.reminderLayout.background = null
                binding.tipLayout.background = null
                binding.dashboardLayout.background = null
                binding.sellLayout.setBackgroundResource(R.drawable.nav_round_bg)

                // set other nav item text visibility
                binding.reminderText.visibility = View.GONE
                binding.tipText.visibility = View.GONE
                binding.dashboardText.visibility = View.GONE

                // set animation
                val scaleAnimation = ScaleAnimation(
                    0.8f,
                    1.0f,
                    1f,
                    1f,
                    Animation.RELATIVE_TO_SELF,
                    1.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f
                )
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                binding.sellLayout.startAnimation(scaleAnimation)

                selectedTab = 2
            }
        }

        binding.tipLayout.setOnClickListener {
            if (selectedTab != 3) {

                // change fragment
                replaceFragment(TipFragment())
                binding.tipText.visibility = View.VISIBLE
                binding.sellLayout.background = null
                binding.reminderText.background = null
                binding.dashboardLayout.background = null
                binding.tipLayout.setBackgroundResource(R.drawable.nav_round_bg)

                // set other nav item text visibility
                binding.sellText.visibility = View.GONE
                binding.reminderText.visibility = View.GONE
                binding.dashboardText.visibility = View.GONE

                // set animation
                val scaleAnimation = ScaleAnimation(
                    0.8f,
                    1.0f,
                    1f,
                    1f,
                    Animation.RELATIVE_TO_SELF,
                    1.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f
                )
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                binding.tipLayout.startAnimation(scaleAnimation)

                selectedTab = 3
            }
        }

        binding.dashboardLayout.setOnClickListener {
            if (selectedTab != 4) {
                // change fragment
                replaceFragment(DashboardFragment())
                binding.dashboardText.visibility = View.VISIBLE
                binding.sellLayout.background = null
                binding.tipLayout.background = null
                binding.reminderLayout.background = null
                binding.dashboardLayout.setBackgroundResource(R.drawable.nav_round_bg)

                // set other nav item text visibility
                binding.sellText.visibility = View.GONE
                binding.tipText.visibility = View.GONE
                binding.reminderText.visibility = View.GONE

                // set animation
                val scaleAnimation = ScaleAnimation(
                    0.8f,
                    1.0f,
                    1f,
                    1f,
                    Animation.RELATIVE_TO_SELF,
                    1.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f
                )
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                binding.dashboardLayout.startAnimation(scaleAnimation)

                selectedTab = 4
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}

