package com.tarotcompany.leaflocus.screens.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.UserPlant

class ShowcaseAdapter(private var plants: List<UserPlant>) : RecyclerView.Adapter<ShowcaseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nickname: TextView = view.findViewById(R.id.textviewNickname) // Assuming item_user_plant.xml has this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_plant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nickname.text = plants[position].customNickname
    }

    override fun getItemCount() = plants.size

    fun updateData(newPlants: List<UserPlant>) {
        this.plants = newPlants
        notifyDataSetChanged()
    }
}