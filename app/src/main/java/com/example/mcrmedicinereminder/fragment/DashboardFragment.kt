package com.example.mcrmedicinereminder.fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mcrmedicinereminder.*
import com.example.mcrmedicinereminder.activity.AccountActivity
import com.example.mcrmedicinereminder.activity.RestockMedicineActivity
import com.example.mcrmedicinereminder.activity.SignIn
import com.example.mcrmedicinereminder.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class DashboardFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var storageref: FirebaseStorage

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
        database = FirebaseDatabase.getInstance()
        storageref = FirebaseStorage.getInstance()
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please Wait..")
        progressDialog.setMessage("Application is loading, please wait")
        progressDialog.show()

        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("Log out ?")
            builder.setMessage("Are you sure you want to Logout ?")
            builder.apply {
                setPositiveButton("YES", DialogInterface.OnClickListener { dialog, id ->
                    // Shared Preference
                    val pref = builder.context.getSharedPreferences(
                        "login",
                        AppCompatActivity.MODE_PRIVATE
                    )
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
            startActivity(Intent(it.context, RestockMedicineActivity::class.java))
        }

        database.reference.child("users").child(firebaseAuth.currentUser?.uid.toString())
            .get().addOnSuccessListener {
                if (it.exists()) {
                    progressDialog.dismiss()
                    val userId = it.child("userId").value
                    val name = it.child("name").value
                    val email = it.child("email").value
                    val imageURL = it.child("imageURL").value

                    binding.userNameTxt.text = "Hello, $name!"
                    if (imageURL != "null") {
                        Glide.with(requireContext()).load(imageURL).into(binding.profileImg)
                    }
                }
            }
    }

}