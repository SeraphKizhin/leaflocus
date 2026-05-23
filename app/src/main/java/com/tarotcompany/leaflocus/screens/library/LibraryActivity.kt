package com.tarotcompany.leaflocus.screens.library

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.AppDatabase
import com.tarotcompany.leaflocus.data.PlantType

class LibraryActivity : AppCompatActivity(), LibraryContract.View {

    private lateinit var presenter: LibraryPresenter
    private lateinit var plantAdapter: PlantAdapter
    private var currentUserId: Int = -1 // Need to pass this from Dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        // Assume we passed the logged-in user's ID via Intent
        currentUserId = intent.getIntExtra("USER_ID", -1)

        val database = AppDatabase.getDatabase(applicationContext)
        presenter = LibraryPresenter(this, database.plantDao())

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPlants)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with an empty list
        plantAdapter = PlantAdapter(emptyList()) { selectedPlant ->
            if (currentUserId != -1) {
                presenter.addPlantToUser(currentUserId, selectedPlant)
            } else {
                showMessage("Error: User not logged in.")
            }
        }
        recyclerView.adapter = plantAdapter

        // Fetch data
        presenter.loadEncyclopedia()
    }

    override fun showPlants(plants: List<PlantType>) {
        plantAdapter.updateData(plants)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}