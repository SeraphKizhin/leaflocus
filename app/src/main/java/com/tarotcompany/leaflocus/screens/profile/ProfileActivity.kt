package com.tarotcompany.leaflocus.screens.profile

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.*

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter
    private lateinit var showcaseAdapter: ShowcaseAdapter
    private lateinit var achievementAdapter: AchievementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize Presenter
        val db = AppDatabase.getDatabase(this)
        presenter = ProfilePresenter(this, db.userDao(), db.plantDao(), db.achievementDao())

        // Setup Back Button
        findViewById<TextView>(R.id.textviewBackToDashboard).setOnClickListener {
            presenter.onBackClicked()
        }

        // Setup RecyclerViews
        val showcaseRecycler = findViewById<RecyclerView>(R.id.recyclerViewShowcase)
        showcaseRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        showcaseAdapter = ShowcaseAdapter(emptyList<UserPlant>())
        showcaseRecycler.adapter = showcaseAdapter

        val achievementRecycler = findViewById<RecyclerView>(R.id.recyclerViewAchievements)
        achievementRecycler.layoutManager = LinearLayoutManager(this)
        achievementAdapter = AchievementAdapter(emptyList<Achievement>())
        achievementRecycler.adapter = achievementAdapter

        findViewById<Button>(R.id.buttonEditProfile).setOnClickListener {
            // 1. Grab the username explicitly into a variable first
            val currentUsername = intent.getStringExtra("username") ?: ""

            // 2. Pass that variable to the new screen
            val editIntent = android.content.Intent(this, EditProfileActivity::class.java)
            editIntent.putExtra("username", currentUsername)
            startActivity(editIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        // This runs every time the screen becomes visible (even when returning from the Edit screen)
        val username = intent.getStringExtra("username") ?: ""
        if (username.isNotEmpty()) {
            presenter.fetchProfileData(username)
        }
    }

    override fun showProfile(user: User, showcased: List<UserPlant>, achievements: List<Achievement>) {
        findViewById<TextView>(R.id.textviewUsername).text = user.username
        findViewById<TextView>(R.id.textviewLevel).text = "Level ${user.level}"
        findViewById<TextView>(R.id.textviewBio).text = user.bio

        showcaseAdapter.updateData(showcased)
        achievementAdapter.updateData(achievements)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateBack() { finish() }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}