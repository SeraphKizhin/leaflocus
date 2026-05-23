package com.tarotcompany.leaflocus.screens.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.UserPlantDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserPlantAdapter(
    private var plants: List<UserPlantDetails>,
    private val onWaterClicked: (Int) -> Unit
) : RecyclerView.Adapter<UserPlantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textNickname: TextView = view.findViewById(R.id.textviewNickname)
        val textSpecies: TextView = view.findViewById(R.id.textviewSpecies)
        val textStatus: TextView = view.findViewById(R.id.textviewWaterStatus)
        val btnWater: Button = view.findViewById(R.id.buttonWater)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_plant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plants[position]
        holder.textNickname.text = plant.customNickname
        holder.textSpecies.text = plant.name

        // Format the timestamp
        val dateString = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(Date(plant.lastWatered))
        holder.textStatus.text = "Last Watered: $dateString\nNeeds: ${plant.waterFrequency}"

        holder.btnWater.setOnClickListener {
            onWaterClicked(plant.plantId)
        }
    }

    override fun getItemCount() = plants.size

    fun updateData(newPlants: List<UserPlantDetails>) {
        plants = newPlants
        notifyDataSetChanged()
    }
}