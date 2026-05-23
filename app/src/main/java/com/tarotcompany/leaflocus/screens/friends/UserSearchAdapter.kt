package com.tarotcompany.leaflocus.screens.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.User

class UserSearchAdapter(
    private var users: List<User>,
    private val onAddClicked: (User) -> Unit,
    private val onUserClicked: (User) -> Unit
) : RecyclerView.Adapter<UserSearchAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textviewUsername: TextView = view.findViewById(R.id.textviewUsername)
        val textviewUid: TextView = view.findViewById(R.id.textviewUid)
        val buttonAddFriend: Button = view.findViewById(R.id.buttonAddFriend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.textviewUsername.text = user.username
        holder.textviewUid.text = "UID: ${user.uid}"

        holder.buttonAddFriend.setOnClickListener { onAddClicked(user) }

        holder.itemView.setOnClickListener { onUserClicked(user) }
    }

    override fun getItemCount() = users.size

    fun updateData(newUsers: List<User>) {
        this.users = newUsers
        notifyDataSetChanged()
    }
}