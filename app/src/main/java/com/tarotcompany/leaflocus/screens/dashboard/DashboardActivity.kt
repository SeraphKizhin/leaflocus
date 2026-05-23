package com.tarotcompany.leaflocus.screens.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.AppDatabase
import com.tarotcompany.leaflocus.data.UserPlantDetails
import com.tarotcompany.leaflocus.screens.library.LibraryActivity
import com.tarotcompany.leaflocus.screens.login.LoginActivity
import com.tarotcompany.leaflocus.screens.profile.ProfileActivity

class DashboardActivity : AppCompatActivity(), DashboardContract.View {

    private lateinit var presenter: DashboardPresenter
    private lateinit var plantAdapter: UserPlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val database = AppDatabase.getDatabase(applicationContext)
        presenter = DashboardPresenter(
            this,
            database.userDao(),
            database.plantDao(),
            database.achievementDao()
        )

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyPlants)
        recyclerView.layoutManager = LinearLayoutManager(this)
        plantAdapter = UserPlantAdapter(emptyList()) { plantId ->
            presenter.waterPlant(plantId)
        }
        recyclerView.adapter = plantAdapter

        // Click Listeners
        findViewById<Button>(R.id.buttonProfile).setOnClickListener { presenter.onProfileClicked() }
        findViewById<Button>(R.id.buttonLogout).setOnClickListener { presenter.onLogoutClicked() }
        findViewById<ImageView>(R.id.imageButtonLibrary).setOnClickListener { presenter.onLibraryClicked() }

        // Initialize with passed username
        val username = intent.getStringExtra("username") ?: ""
        findViewById<TextView>(R.id.textviewWelcome).text = "Welcome, $username!"
        presenter.initialize(username)
    }

    // Refresh data when returning from the Library
    override fun onResume() {
        super.onResume()
        val username = intent.getStringExtra("username") ?: ""
        presenter.initialize(username)
    }

    override fun showUserPlants(plants: List<UserPlantDetails>) {
        plantAdapter.updateData(plants)
    }

    override fun navigateToProfile(username: String) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }

    override fun navigateToLibrary(userId: Int) {
        val intent = Intent(this, LibraryActivity::class.java)
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}