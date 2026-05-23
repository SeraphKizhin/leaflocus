package com.tarotcompany.leaflocus.screens.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.Achievement

class AchievementAdapter(private var achievements: List<Achievement>) : RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textviewTitle)
        val desc: TextView = view.findViewById(R.id.textviewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_achievement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = achievements[position]
        holder.title.text = item.title
        holder.desc.text = item.description
    }

    override fun getItemCount() = achievements.size

    fun updateData(newAchievements: List<Achievement>) {
        this.achievements = newAchievements
        notifyDataSetChanged()
    }
}