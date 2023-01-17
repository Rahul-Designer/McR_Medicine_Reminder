package com.example.mcrmedicinereminder.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcrmedicinereminder.Constants
import com.example.mcrmedicinereminder.R
import com.example.mcrmedicinereminder.adapter.MedicineTypesAdapter
import com.example.mcrmedicinereminder.databinding.ActivityMedicineBinding
import kotlinx.android.synthetic.main.activity_medicine.view.*

class MedicineActivity : AppCompatActivity(), MedicineTypesAdapter.OnItemClickListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMedicineBinding
    private val medicineUnit: Array<String> =
        arrayOf<String>("tabs", "syrup", "times", "mL(CC)", "drops", "balls", "other")

    private val instructionArray: Array<String> = arrayOf(
        "No Instructions",
        "Take before meal",
        "Take after meal",
        "Take on an empty stomach",
        "Take with water",
        "Never take with milk",
        "Avoid Sugars",
        "Avoid fatty food",
        "Eat more vegetables",
        "Eat more iron-rich foods"
    )

    private var unit: String = "tabs"

    private lateinit var adapter: MedicineTypesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medicine)


        // Medicine Type Recyclerview
        binding.medicineImgsRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = MedicineTypesAdapter(this, Constants.getMedicineType(), this)
        binding.medicineImgsRecyclerview.adapter = adapter

        // Schedule Activity
        binding.scheduleBtn.setOnClickListener {
            val intent = Intent(it.context, ScheduleActivity::class.java)
            startActivity(intent)
        }


        // Dose Unit Spinner

        binding.doseSpinner.onItemSelectedListener = this
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, medicineUnit)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        binding.doseSpinner.adapter = adapter

        // Current Stock
        binding.stockQtyTxt.setOnClickListener {
            val dialog = Dialog(it.context)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.stock_unit)
            val defaultQty = "12"
            val stockQty = dialog.findViewById(R.id.stock_qty_edt) as EditText
            val stockUnit = dialog.findViewById(R.id.stock_unit) as TextView
            val adjust = dialog.findViewById(R.id.set_Qty) as Button
            adjust.setOnClickListener {
                val qty = stockQty.text.toString()
//                Toast.makeText(this,unit,Toast.LENGTH_SHORT).show()
                binding.stockQtyTxt.text = qty + " " + unit
                dialog.dismiss()
            }
            stockUnit.text = unit
            dialog.show()
        }

        val instructionAdapter = ArrayAdapter(this,R.layout.instruction_spinner_list,instructionArray)
        instructionAdapter.setDropDownViewResource(R.layout.instruction_spinner_list)
        binding.instructionSpn.adapter = instructionAdapter

    }

    override fun updateBackground(name: String, position: Int) {
        binding.medicineName.text = name
        adapter.changeUI(position)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        this.unit = medicineUnit[p2]
        binding.stockQtyTxt.text = "12 " + medicineUnit[p2]

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}