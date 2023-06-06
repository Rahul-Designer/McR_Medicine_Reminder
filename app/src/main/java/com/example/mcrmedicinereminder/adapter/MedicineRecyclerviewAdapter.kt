package com.example.mcrmedicinereminder.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mcrmedicinereminder.R
import com.example.mcrmedicinereminder.data.MedicineReminder
import kotlinx.android.synthetic.main.medicine_reminder_recyclerview_item.view.*
import com.example.mcrmedicinereminder.Constants

class MedicineRecyclerviewAdapter(
    private val context: Context,
    private val onClickItem: OnClickItem
) :
    RecyclerView.Adapter<MedicineRecyclerviewAdapter.ViewHolder>() {

    var arrMedicineReminder = ArrayList<MedicineReminder>()

    class ViewHolder(itemView: View, private val onClickItem: OnClickItem) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(info: MedicineReminder) {
            itemView.medicine_image.setImageResource(Constants.getMedicineType()[info.medicineImage].image)
            itemView.medicine_name.text = info.medicineName
            itemView.medicine_qnty.text = info.medicineQnty
            itemView.medicine_instruction.text = info.medicineInstruction
            itemView.stock_size.text = info.stockSize.toString()
            itemView.time.text = info.medicineTime

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.medicine_reminder_recyclerview_item, parent, false), onClickItem
        )
    }

    override fun getItemCount(): Int {
        return arrMedicineReminder.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = arrMedicineReminder[position]
        holder.bind(pos)
        if (pos.medicineCheck) {
            holder.itemView.enable_btn.visibility = View.VISIBLE
            holder.itemView.disable_btn.visibility = View.GONE
        } else {
            holder.itemView.disable_btn.visibility = View.VISIBLE
            holder.itemView.enable_btn.visibility = View.GONE
        }
        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete this item ?")
            builder.apply {
                setPositiveButton("YES", DialogInterface.OnClickListener { dialog, id ->
                    onClickItem.deleteRow(arrMedicineReminder.get(position), position)

                })
            }
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
            builder.show()
            return@setOnLongClickListener true
        }

        holder.itemView.medicine_check.setOnClickListener {
            if (!pos.medicineCheck){
                val default_medicine_name = pos.medicineName.toString()
                val stockSize = pos.stockSize - 1
                pos.medicineCheck = true
                onClickItem.updateMedicineCheck(pos, position)
                onClickItem.updateMedicineStatus(default_medicine_name, stockSize)
            }
        }
    }

    fun updateMedicineList(medicineReminder: List<MedicineReminder>) {
        arrMedicineReminder.clear()
        arrMedicineReminder.addAll(medicineReminder)
        notifyDataSetChanged()
    }

    interface OnClickItem {
        fun deleteRow(medicineReminder: MedicineReminder, position: Int)
        fun updateMedicineCheck(medicineReminder: MedicineReminder, position: Int)
        fun updateMedicineStatus(medicineName: String, medicineStock: Int)
    }

}