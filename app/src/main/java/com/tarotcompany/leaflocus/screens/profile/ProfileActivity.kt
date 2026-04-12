package com.tarotcompany.leaflocus.screens.profile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tarotcompany.leaflocus.R

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize Presenter
        presenter = ProfilePresenter(this)

        val textviewBackToDashboard = findViewById<TextView>(R.id.textviewBackToDashboard)
        textviewBackToDashboard.setOnClickListener {
            presenter.onBackClicked()
        }

        // Use Presenter to load data
        presenter.fetchProfileData()
    }

    override fun showUserDetails(email: String, joinDate: String) {
        findViewById<TextView>(R.id.textviewEmail).text = email
    }

    override fun navigateBack() {
        finish()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}