package com.tarotcompany.leaflocus.screens.profile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.AppDatabase

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 1. Initialize Database and DAO
        val database = AppDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()

        // 2. Initialize Presenter with DAO
        presenter = ProfilePresenter(this, userDao)

        val textviewBackToDashboard = findViewById<TextView>(R.id.textviewBackToDashboard)
        textviewBackToDashboard.setOnClickListener {
            presenter.onBackClicked()
        }

        // 3. Get the username passed from Dashboard and fetch data
        val username = intent.getStringExtra("username") ?: ""
        presenter.fetchProfileData(username)
    }

    override fun showUserDetails(username: String, email: String) {
        // Dynamically bind the fetched data to your XML TextViews
        findViewById<TextView>(R.id.textviewUsernameLabel).text = "Username: $username"
        findViewById<TextView>(R.id.textviewEmail).text = "Email: $email"

        // Note: You can bind the First/Middle/Last names here later once you add them to the Room Entity!
    }

    override fun navigateBack() {
        finish()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}