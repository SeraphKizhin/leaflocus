package com.tarotcompany.leaflocus.screens.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.UserPlant

class ShowcaseSelectionAdapter(
    private var plants: List<UserPlant>,
    private val onPlantToggled: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<ShowcaseSelectionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantName: TextView = view.findViewById(R.id.textviewPlantName)
        val checkbox: CheckBox = view.findViewById(R.id.checkboxShowcase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_showcase_selection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plants[position]
        holder.plantName.text = plant.customNickname

        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = plant.isShowcased

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onPlantToggled(plant.id, isChecked)
        }
    }

    override fun getItemCount() = plants.size

    fun updateData(newPlants: List<UserPlant>) {
        this.plants = newPlants
        notifyDataSetChanged()
    }
}