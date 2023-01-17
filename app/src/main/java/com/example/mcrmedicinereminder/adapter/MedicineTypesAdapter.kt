package com.example.mcrmedicinereminder.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mcrmedicinereminder.R
import com.example.mcrmedicinereminder.model.MedicineTypes
import kotlinx.android.synthetic.main.medicine_types.view.*

class MedicineTypesAdapter(
    private val context: Context,
    private val arrMedicineTypes: ArrayList<MedicineTypes>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MedicineTypesAdapter.ViewHolder>() {
    private var rmPosition: Int? = 1
    private var last: Int? = null

    class ViewHolder(itemView: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
//                it.item_bg.setBackgroundResource(R.drawable.selected_medicine_bg)
                val medicineName = it.item_txt.text.toString()
                Log.d("RAHUL", medicineName)
                onItemClickListener.updateBackground(medicineName,adapterPosition)
            }
        }

        fun bind(info: MedicineTypes) {
            itemView.item_txt.text = info.name
            itemView.item_image.setImageResource(info.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.medicine_types, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (rmPosition == position || rmPosition == last) {
            holder.itemView.item_bg.setBackgroundResource(R.drawable.selected_medicine_bg)
        }
        if (last == position ) {
            holder.itemView.item_bg.setBackgroundColor(Color.WHITE)
        }

        val pos = arrMedicineTypes[position]
        holder.bind(pos)
    }

    override fun getItemCount(): Int {
        return arrMedicineTypes.size
    }

    interface OnItemClickListener {
        fun updateBackground(name : String ,position: Int)
    }

    fun changeUI(position: Int) {
        last = this.rmPosition
        this.rmPosition = position
        last?.let { notifyItemChanged(it) }
        notifyItemChanged(position)
    }
}