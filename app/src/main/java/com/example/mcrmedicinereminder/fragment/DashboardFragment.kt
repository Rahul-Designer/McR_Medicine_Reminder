package com.example.mcrmedicinereminder.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mcrmedicinereminder.*
import com.example.mcrmedicinereminder.activity.AccountActivity
import com.example.mcrmedicinereminder.activity.RestockMedicineActivity
import com.example.mcrmedicinereminder.activity.SignIn
import com.example.mcrmedicinereminder.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
                val builder = AlertDialog.Builder(it.context)
                builder.setTitle("Log out ?")
                builder.setMessage("Are you sure you want to Logout ?")
                builder.apply {
                    setPositiveButton("YES", DialogInterface.OnClickListener { dialog, id ->
                        // Shared Preference
                        val pref = builder.context.getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)
                        val editor = pref?.edit()
                        editor?.putBoolean("flag", false)
                        editor?.apply()
                        startActivity(Intent(builder.context, SignIn::class.java))
                        activity?.finish()

                    })
                }
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                builder.show()
        }

        binding.accountTxt.setOnClickListener {
            val intent = Intent(it.context, AccountActivity::class.java)
            startActivity(intent)
        }

        binding.restoreMedicine.setOnClickListener {
            startActivity(Intent(it.context,RestockMedicineActivity::class.java))
        }
    }

}