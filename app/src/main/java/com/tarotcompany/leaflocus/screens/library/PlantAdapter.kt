package com.tarotcompany.leaflocus.screens.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.PlantType

class PlantAdapter(
    private var plants: List<PlantType>,
    private val onAddClicked: (PlantType) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.textviewPlantName)
        val textScientific: TextView = view.findViewById(R.id.textviewScientificName)
        val textWater: TextView = view.findViewById(R.id.textviewWaterFrequency)
        val textDifficulty: TextView = view.findViewById(R.id.textviewDifficulty)
        val btnAdd: Button = view.findViewById(R.id.buttonAddPlant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.textName.text = plant.name
        holder.textScientific.text = plant.scientificName
        holder.textWater.text = "Water: ${plant.waterFrequency}"
        holder.textDifficulty.text = "Difficulty: ${plant.growthDifficulty}"

        holder.btnAdd.setOnClickListener {
            onAddClicked(plant)
        }
    }

    override fun getItemCount() = plants.size

    fun updateData(newPlants: List<PlantType>) {
        plants = newPlants
        notifyDataSetChanged()
    }
}