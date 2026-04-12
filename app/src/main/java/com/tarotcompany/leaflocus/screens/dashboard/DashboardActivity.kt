package com.tarotcompany.leaflocus.screens.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.screens.login.LoginActivity
import com.tarotcompany.leaflocus.screens.profile.ProfileActivity

class DashboardActivity : AppCompatActivity(), DashboardContract.View {
    private lateinit var presenter: DashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        presenter = DashboardPresenter(this)

        val textviewProfile = findViewById<TextView>(R.id.textviewProfile)
        val textviewLogoutLink = findViewById<TextView>(R.id.textviewLogoutLink)

        textviewProfile.setOnClickListener {
            presenter.onProfileClicked()
        }

        textviewLogoutLink.setOnClickListener {
            presenter.onLogoutClicked()
        }

        val username = intent.getStringExtra("username")
        presenter.loadUserData(username)
    }

    override fun displayWelcomeMessage(username: String) {
        findViewById<TextView>(R.id.textviewWelcome).text = "Welcome, $username!"
    }

    override fun navigateToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}