package com.example.mcrmedicinereminder.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcrmedicinereminder.Constants
import com.example.mcrmedicinereminder.R
import com.example.mcrmedicinereminder.adapter.MedicineTypesAdapter
import com.example.mcrmedicinereminder.databinding.ActivityMedicineBinding


class MedicineActivity : AppCompatActivity(), MedicineTypesAdapter.OnItemClickListener {
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
    private lateinit var unit: String
    private lateinit var adapter: MedicineTypesAdapter
    private lateinit var medicineName: String
    private var medicineType: String = "Tablet"
    private var stockSize: String = "12 tabs"
    private lateinit var doseUnit: String
    private lateinit var medicineSchedule: String
    private lateinit var medicineInstruction: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medicine)

        // Back Button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }


        // Medicine Type Recyclerview
        binding.medicineImgsRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = MedicineTypesAdapter(this, Constants.getMedicineType(), this)
        binding.medicineImgsRecyclerview.adapter = adapter


        // Dose Unit Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, medicineUnit)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        binding.doseSpinner.adapter = adapter

        // Dose Spinner item Selected
        binding.doseSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    unit = medicineUnit[pos]
                    doseUnit = medicineUnit[pos]
                    binding.stockQtyTxt.text = "12 " + medicineUnit[pos]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    doseUnit = "tabs"
                }
            }

        // Current Stock
        binding.stockQtyTxt.setOnClickListener {
            val dialog = Dialog(it.context)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.stock_unit)
            val stockQty = dialog.findViewById(R.id.stock_qty_edt) as EditText
            val stockUnit = dialog.findViewById(R.id.stock_unit) as TextView
            val adjust = dialog.findViewById(R.id.set_Qty) as Button
            adjust.setOnClickListener {
                val qty = stockQty.text.toString()
                binding.stockQtyTxt.text = qty + " " + unit
                this.stockSize = qty + " " + unit
                dialog.dismiss()
            }
            stockUnit.text = unit
            dialog.show()
        }


        medicineSchedule = binding.scheduleTxt.text.toString()
        // Schedule Activity
        binding.scheduleBtn.setOnClickListener {
            val intent = Intent(it.context, ScheduleActivity::class.java)
            startActivityForResult(intent, 1)

        }


        // Instruction Spinner
        val instructionAdapter =
            ArrayAdapter(this, R.layout.instruction_spinner_list, instructionArray)
        instructionAdapter.setDropDownViewResource(R.layout.instruction_spinner_list)
        binding.instructionSpn.adapter = instructionAdapter

        //  Instructions Item Selected
        binding.instructionSpn.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    medicineInstruction = instructionArray[pos].toString()
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    medicineInstruction = "No Instruction"
                }
            }


        // Add Button
        binding.addBtn.setOnClickListener {
            getMedicineDetail()
        }
    }


    override fun updateBackground(name: String, position: Int) {
        this.medicineType = name
        binding.medicineType.text = name
        adapter.changeUI(position)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Change were made in the medication.")
        builder.apply {
            setPositiveButton("SAVE", DialogInterface.OnClickListener { dialog, id ->
                finish()
            })
        }
            .setNegativeButton(
                "DISCARD",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    finish()
                    dialogInterface.dismiss()
                })
        builder.show()
    }

    private fun getMedicineDetail() {
        if ((binding.medicineName.text.toString()).isEmpty()) {
            binding.medicineName.error = "Medicine Name"
        } else {
            this.medicineName = binding.medicineName.text.toString()
            Toast.makeText(
                this,
                "$medicineName\n $medicineType\n $doseUnit\n $stockSize\n $medicineSchedule\n $medicineInstruction",
                Toast.LENGTH_LONG
            )
                .show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (intent != null) {
                    binding.scheduleTxt.setTextColor(resources.getColor(R.color.colour1))
                    binding.scheduleTxt.text = intent.getBundleExtra("Bundle")
                        ?.getString("schedulemessage", "Not Selected")
                    var medicineTimeTable: String? = null
                    when (intent.getBundleExtra("Bundle")
                        ?.getString("scheduleset", "Not Selected")) {
                        "One Times" -> {
                            binding.medicineTimeOne.visibility = View.VISIBLE
                            binding.timeOne.text =
                                intent.getBundleExtra("Bundle")?.getString("time1", "07:00 AM")
                            binding.timeOneTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet1", "1 tab")
                            binding.medicineTimeTwo.visibility = View.GONE
                            binding.medicineTimeThree.visibility = View.GONE
                            binding.medicineTimeFour.visibility = View.GONE
                            medicineTimeTable =
                                binding.timeOne.text.toString() + " -> " + binding.timeOneTab.text.toString()

                        }
                        "Two Times" -> {
                            binding.medicineTimeOne.visibility = View.VISIBLE
                            binding.medicineTimeTwo.visibility = View.GONE
                            binding.timeOne.text =
                                intent.getBundleExtra("Bundle")?.getString("time1", "07:00 AM")
                            binding.timeOneTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet1", "1 tab")
                            binding.timeThree.text =
                                intent.getBundleExtra("Bundle")?.getString("time3", "01:00 PM")
                            binding.timeThreeTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet3", "1 tab")
                            binding.medicineTimeThree.visibility = View.VISIBLE
                            binding.medicineTimeFour.visibility = View.GONE
                            medicineTimeTable =
                                binding.timeOne.text.toString() + " -> " + binding.timeOneTab.text.toString() + "\n" + binding.timeTwo.text.toString() + " -> " + binding.timeTwoTab.text.toString()
                        }
                        "Three Times" -> {
                            binding.medicineTimeOne.visibility = View.VISIBLE
                            binding.medicineTimeTwo.visibility = View.VISIBLE
                            binding.medicineTimeThree.visibility = View.VISIBLE
                            binding.timeOne.text =
                                intent.getBundleExtra("Bundle")?.getString("time1", "07:00 AM")
                            binding.timeOneTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet1", "1 tab")
                            binding.timeTwo.text =
                                intent.getBundleExtra("Bundle")?.getString("time2", "01:00 PM")
                            binding.timeTwoTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet2", "1 tab")
                            binding.timeThree.text =
                                intent.getBundleExtra("Bundle")?.getString("time3", "07:00 PM")
                            binding.timeThreeTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet3", "1 tab")
                            binding.medicineTimeFour.visibility = View.GONE
                            medicineTimeTable =
                                binding.timeOne.text.toString() + " -> " + binding.timeOneTab.text.toString() + "\n" + binding.timeTwo.text.toString() + " -> " + binding.timeTwoTab.text.toString() + "\n" + binding.timeThree.text.toString() + " -> " + binding.timeThreeTab.text.toString()
                        }
                        "Four Times" -> {
                            binding.medicineTimeOne.visibility = View.VISIBLE
                            binding.medicineTimeTwo.visibility = View.VISIBLE
                            binding.medicineTimeThree.visibility = View.VISIBLE
                            binding.medicineTimeFour.visibility = View.VISIBLE
                            binding.timeOne.text =
                                intent.getBundleExtra("Bundle")?.getString("time1", "07:00 AM")
                            binding.timeOneTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet1", "1 tab")
                            binding.timeTwo.text =
                                intent.getBundleExtra("Bundle")?.getString("time2", "01:00 PM")
                            binding.timeTwoTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet2", "1 tab")
                            binding.timeThree.text =
                                intent.getBundleExtra("Bundle")?.getString("time3", "07:00 PM")
                            binding.timeThreeTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet3", "1 tab")
                            binding.timeFour.text =
                                intent.getBundleExtra("Bundle")?.getString("time4", "10:00 PM")
                            binding.timeFourTab.text =
                                intent.getBundleExtra("Bundle")?.getString("tablet4", "1 tab")
                            medicineTimeTable =
                                binding.timeOne.text.toString() + " -> " + binding.timeOneTab.text.toString() + "\n" + binding.timeTwo.text.toString() + " -> " + binding.timeTwoTab.text.toString() + "\n" + binding.timeThree.text.toString() + " -> " + binding.timeThreeTab.text.toString() + "\n" + binding.timeFour.text.toString() + " -> " + binding.timeFourTab.text.toString()
                        }
                    }
                    binding.scheduleTimingStart.visibility = View.VISIBLE
                    binding.starttime.visibility = View.VISIBLE
                    binding.starttime.text = intent.getBundleExtra("Bundle")
                        ?.getString("durationstart", "No Specific Date")
                    binding.scheduleTimingEnd.visibility = View.VISIBLE
                    binding.endtime.visibility = View.VISIBLE
                    binding.endtime.text = intent.getBundleExtra("Bundle")
                        ?.getString("durationend", "No Specific Date")

                    medicineSchedule =
                        binding.scheduleTxt.text.toString() + "\n" + medicineTimeTable + "\n" + binding.starttime.text + "\n" +  binding.endtime.text
                }
            }
        }
    }
}