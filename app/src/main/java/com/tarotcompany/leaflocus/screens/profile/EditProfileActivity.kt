package com.tarotcompany.leaflocus.screens.profile

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.AppDatabase
import com.tarotcompany.leaflocus.data.UserPlant

class EditProfileActivity : AppCompatActivity(), EditProfileContract.View {

    private lateinit var presenter: EditProfilePresenter
    private lateinit var adapter: ShowcaseSelectionAdapter

    // A map to track which plants the user checked/unchecked during this session
    private val toggledPlants = mutableMapOf<Int, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val db = AppDatabase.getDatabase(this)
        presenter = EditProfilePresenter(this, db.userDao(), db.plantDao())

        // 1. Setup Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewShowcaseSelection)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShowcaseSelectionAdapter(emptyList()) { plantId, isChecked ->
            // Save the user's choice to our map
            toggledPlants[plantId] = isChecked
        }
        recyclerView.adapter = adapter

        // 2. Setup Buttons
        findViewById<TextView>(R.id.textviewCancel).setOnClickListener { closeScreen() }

        findViewById<TextView>(R.id.textviewSave).setOnClickListener {
            val newBio = findViewById<EditText>(R.id.edittextBio).text.toString()
            presenter.saveChanges(presenter.currentUserId, newBio, toggledPlants)
        }

        // 3. Load Data
        val username = intent.getStringExtra("username") ?: ""
        presenter.loadUserData(username)
    }

    override fun showCurrentBio(bio: String) {
        findViewById<EditText>(R.id.edittextBio).setText(bio)
    }

    override fun showUserPlants(plants: List<UserPlant>) {
        // This will pop up a message telling us EXACTLY how many plants it found
        Toast.makeText(this, "Debug: Found ${plants.size} plants", Toast.LENGTH_SHORT).show()

        adapter.updateData(plants)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun closeScreen() { finish() }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}