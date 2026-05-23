package com.tarotcompany.leaflocus.screens.library

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Required import
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.AppDatabase
import com.tarotcompany.leaflocus.data.PlantType
import kotlinx.coroutines.launch // Required import

class LibraryActivity : AppCompatActivity(), LibraryContract.View {

    private lateinit var presenter: LibraryPresenter
    private lateinit var plantAdapter: PlantAdapter
    private var currentUserId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        currentUserId = intent.getIntExtra("USER_ID", -1)

        val database = AppDatabase.getDatabase(applicationContext)
        presenter = LibraryPresenter(
            this,
            database.plantDao(),
            database.achievementDao()
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPlants)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backButton = findViewById<TextView>(R.id.textviewBack)
        backButton.setOnClickListener { finish() }

        plantAdapter = PlantAdapter(emptyList()) { selectedPlant ->
            if (currentUserId != -1) {
                presenter.addPlantToUser(currentUserId, selectedPlant)
            } else {
                showMessage("Error: User not logged in.")
            }
        }
        recyclerView.adapter = plantAdapter

        // FIXED: Using lifecycleScope instead of 'scope'
        lifecycleScope.launch {
            val plantDao = database.plantDao()
            val existingPlants = plantDao.getAllPlantTypes()
            if (existingPlants.isEmpty()) {
                plantDao.insertPlantTypes(AppDatabase.initialPlants)
                presenter.loadEncyclopedia()
            } else {
                presenter.loadEncyclopedia()
            }
        }
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