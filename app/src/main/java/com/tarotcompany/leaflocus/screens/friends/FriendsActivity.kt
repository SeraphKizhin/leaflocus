package com.tarotcompany.leaflocus.screens.friends

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.AppDatabase
import com.tarotcompany.leaflocus.data.User

class FriendsActivity : AppCompatActivity(), FriendsContract.View {

    private lateinit var presenter: FriendsPresenter
    private lateinit var adapter: UserSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        val db = AppDatabase.getDatabase(this)
        presenter = FriendsPresenter(this, db.userDao(), db.friendDao())

        val currentUsername = intent.getStringExtra("username") ?: ""
        presenter.initializeUser(currentUsername)

        findViewById<TextView>(R.id.textviewBack).setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = UserSearchAdapter(
            users = emptyList(),
            onAddClicked = { targetUser ->
                presenter.addFriend(targetUser)
            },
            onUserClicked = { targetUser ->
                val intent = android.content.Intent(this, com.tarotcompany.leaflocus.screens.profile.ProfileActivity::class.java)

                intent.putExtra("username", targetUser.username)
                intent.putExtra("isOwnProfile", false)

                startActivity(intent)
            }
        )
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.buttonSearch).setOnClickListener {
            val query = findViewById<EditText>(R.id.edittextSearch).text.toString().trim()
            presenter.search(query)
        }

        val tabFriends = findViewById<Button>(R.id.buttonTabFriends)
        val tabAdd = findViewById<Button>(R.id.buttonTabAdd)
        val searchLayout = findViewById<LinearLayout>(R.id.layoutSearchBar)
        val sectionTitle = findViewById<TextView>(R.id.textviewSectionTitle)

        tabFriends.setOnClickListener {
            searchLayout.visibility = android.view.View.GONE
            sectionTitle.text = "My Friends"

            tabFriends.backgroundTintList = android.content.res.ColorStateList.valueOf(androidx.core.content.ContextCompat.getColor(this, R.color.leaf_primary))
            tabFriends.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.surface_white))

            tabAdd.backgroundTintList = android.content.res.ColorStateList.valueOf(androidx.core.content.ContextCompat.getColor(this, R.color.surface_white))
            tabAdd.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.leaf_primary))

            presenter.loadFriends()
        }

        tabAdd.setOnClickListener {
            searchLayout.visibility = android.view.View.VISIBLE
            sectionTitle.text = "Explore"

            tabAdd.backgroundTintList = android.content.res.ColorStateList.valueOf(androidx.core.content.ContextCompat.getColor(this, R.color.leaf_primary))
            tabAdd.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.surface_white))

            tabFriends.backgroundTintList = android.content.res.ColorStateList.valueOf(androidx.core.content.ContextCompat.getColor(this, R.color.surface_white))
            tabFriends.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.leaf_primary))

            adapter.updateData(emptyList())
        }
    }

    override fun showSearchResults(users: List<User>) {
        adapter.updateData(users)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}