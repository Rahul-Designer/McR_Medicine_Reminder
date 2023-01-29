package com.example.mcrmedicinereminder.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.mcrmedicinereminder.R
import com.example.mcrmedicinereminder.databinding.ActivityScheduleBinding
import java.text.SimpleDateFormat
import java.util.*

class ScheduleActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityScheduleBinding
    private val frequencyArray: Array<String> = arrayOf(
        "1",
        "2",
        "3",
        "4",
    )
    private lateinit var medicineFrequency: String
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.US)
    private val tabletFrequencyFormatter = SimpleDateFormat("hh:mm a", Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        medicineFrequencyTime()
        tabletAdjustment()
        durationTime()


        // Frequency Spinner
        val frequencyAdapter =
            ArrayAdapter(this, R.layout.instruction_spinner_list, frequencyArray)
        frequencyAdapter.setDropDownViewResource(R.layout.instruction_spinner_list)
        binding.frequencySpn.adapter = frequencyAdapter

        binding.frequencySpn.setSelection(2)

        binding.frequencySpn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                medicineFrequency = frequencyArray[p2].toString()
                val medicineFrequency = binding.frequencySpn.selectedItem.toString()
                when (medicineFrequency) {
                    "1" -> {
                        binding.medicineTimeOne.visibility = View.VISIBLE
                        binding.medicineTimeTwo.visibility = View.GONE
                        binding.medicineTimeThree.visibility = View.GONE
                        binding.medicineTimeFour.visibility = View.GONE
                    }
                    "2" -> {
                        binding.medicineTimeOne.visibility = View.VISIBLE
                        binding.medicineTimeTwo.visibility = View.VISIBLE
                        binding.medicineTimeThree.visibility = View.GONE
                        binding.medicineTimeFour.visibility = View.GONE
                    }
                    "3" -> {
                        binding.medicineTimeOne.visibility = View.VISIBLE
                        binding.medicineTimeTwo.visibility = View.VISIBLE
                        binding.medicineTimeThree.visibility = View.VISIBLE
                        binding.medicineTimeFour.visibility = View.GONE
                    }
                    "4" -> {
                        binding.medicineTimeOne.visibility = View.VISIBLE
                        binding.medicineTimeTwo.visibility = View.VISIBLE
                        binding.medicineTimeThree.visibility = View.VISIBLE
                        binding.medicineTimeFour.visibility = View.VISIBLE
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.durationStart.text = formatter.format(calendar.timeInMillis)
    }

    private fun tabletAdjustment() {

        binding.timeOneTab.setOnClickListener {
            val dialog = Dialog(it.context)
            dialog.setContentView(R.layout.tablet_adjustment)
            val stockQty = dialog.findViewById(R.id.stock_qty_edt) as EditText
            val stockUnit = dialog.findViewById(R.id.stock_unit) as TextView
            val adjust = dialog.findViewById(R.id.set_Qty) as Button
            adjust.setOnClickListener {
                val qty = stockQty.text.toString()
                binding.timeOneTab.text = qty + " tab"
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.timeTwoTab.setOnClickListener {
            val dialog = Dialog(it.context)
            dialog.setContentView(R.layout.tablet_adjustment)
            val stockQty = dialog.findViewById(R.id.stock_qty_edt) as EditText
            val stockUnit = dialog.findViewById(R.id.stock_unit) as TextView
            val adjust = dialog.findViewById(R.id.set_Qty) as Button
            adjust.setOnClickListener {
                val qty = stockQty.text.toString()
                binding.timeTwoTab.text = qty + " tab"
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.timeThreeTab.setOnClickListener {
            val dialog = Dialog(it.context)
            dialog.setContentView(R.layout.tablet_adjustment)
            val stockQty = dialog.findViewById(R.id.stock_qty_edt) as EditText
            val stockUnit = dialog.findViewById(R.id.stock_unit) as TextView
            val adjust = dialog.findViewById(R.id.set_Qty) as Button
            adjust.setOnClickListener {
                val qty = stockQty.text.toString()
                binding.timeThreeTab.text = qty + " tab"
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.timeFourTab.setOnClickListener {
            val dialog = Dialog(it.context)
            dialog.setContentView(R.layout.tablet_adjustment)
            val stockQty = dialog.findViewById(R.id.stock_qty_edt) as EditText
            val stockUnit = dialog.findViewById(R.id.stock_unit) as TextView
            val adjust = dialog.findViewById(R.id.set_Qty) as Button
            adjust.setOnClickListener {
                val qty = stockQty.text.toString()
                binding.timeFourTab.text = qty + " tab"
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Change were made in the schedule.")
        builder.apply {
            setPositiveButton("SAVE", DialogInterface.OnClickListener { dialog, id ->
                finish()
            })
        }
            .setNegativeButton("DISCARD", DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
                dialogInterface.dismiss()
            })
        builder.show()

    }

    private fun medicineFrequencyTime() {
        binding.timeOne.setOnClickListener {
            TimePickerDialog(
                it.context,
                object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, p1)
                            set(Calendar.MINUTE, p2)
                        }
                        binding.timeOne.text =
                            tabletFrequencyFormatter.format(calendar.timeInMillis)
                    }

                },
                7,
                0,
                false
            ).show()
        }
        binding.timeTwo.setOnClickListener {
            TimePickerDialog(
                it.context,
                object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, p1)
                            set(Calendar.MINUTE, p2)
                        }
                        binding.timeTwo.text =
                            tabletFrequencyFormatter.format(calendar.timeInMillis)
                    }

                },
                1,
                0,
                false
            ).show()
        }
        binding.timeThree.setOnClickListener {
            TimePickerDialog(
                it.context,
                object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, p1)
                            set(Calendar.MINUTE, p2)
                        }
                        binding.timeThree.text =
                            tabletFrequencyFormatter.format(calendar.timeInMillis)
                    }

                },
                7,
                0,
                false
            ).show()
            binding.timeFour.setOnClickListener {
                TimePickerDialog(
                    it.context,
                    object : TimePickerDialog.OnTimeSetListener {
                        override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                            calendar.apply {
                                set(Calendar.HOUR_OF_DAY, p1)
                                set(Calendar.MINUTE, p2)
                            }
                            binding.timeFour.text =
                                tabletFrequencyFormatter.format(calendar.timeInMillis)
                        }

                    },
                    10,
                    0,
                    false
                ).show()
            }
        }
    }

    private fun durationTime() {
        binding.durationStart.setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.durationEnd.setOnClickListener {
            DatePickerDialog(
                this,
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        calendar.set(p1, p2, p3)
                        binding.durationEnd.text = formatter.format(calendar.timeInMillis)
                        if (p0 != null) {
                            TimePickerDialog(
                                p0.context,
                                object : TimePickerDialog.OnTimeSetListener {
                                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                                        calendar.apply {
                                            set(Calendar.HOUR_OF_DAY, p1)
                                            set(Calendar.MINUTE, p2)
                                        }
                                        binding.durationEnd.text =
                                            formatter.format(calendar.timeInMillis)
                                    }

                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                false
                            ).show()
                        }
                    }

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(p1, p2, p3)
        displayFormattedDate(calendar.timeInMillis)
        TimePickerDialog(
            this,
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }

    private fun displayFormattedDate(timestamp: Long) {
        binding.durationStart.text = formatter.format(timestamp)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, p1)
            set(Calendar.MINUTE, p2)
        }
        displayFormattedDate(calendar.timeInMillis)
    }


}