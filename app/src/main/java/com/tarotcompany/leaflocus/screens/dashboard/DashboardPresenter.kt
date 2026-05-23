package com.tarotcompany.leaflocus.screens.dashboard

import com.tarotcompany.leaflocus.data.PlantDao
import com.tarotcompany.leaflocus.data.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardPresenter(
    private var view: DashboardContract.View?,
    private val userDao: UserDao,
    private val plantDao: PlantDao
) : DashboardContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)
    private var currentUsername: String = ""
    private var currentUserId: Int = -1

    override fun initialize(username: String) {
        currentUsername = username
        loadDashboardData()
    }

    private fun loadDashboardData() {
        scope.launch {
            // Get user ID based on username
            val userId = withContext(Dispatchers.IO) { userDao.getUserId(currentUsername) }

            if (userId != null) {
                currentUserId = userId
                // Fetch their plants
                val plants = withContext(Dispatchers.IO) { plantDao.getUserPlants(userId) }
                view?.showUserPlants(plants)
            }
        }
    }

    override fun waterPlant(plantId: Int) {
        scope.launch {
            val currentTime = System.currentTimeMillis()
            withContext(Dispatchers.IO) { plantDao.updateWateringTime(plantId, currentTime) }
            // Reload the list to show the updated time
            loadDashboardData()
        }
    }

    override fun onProfileClicked() { view?.navigateToProfile(currentUsername) }

    override fun onLibraryClicked() {
        if (currentUserId != -1) { view?.navigateToLibrary(currentUserId) }
    }

    override fun onLogoutClicked() { view?.navigateToLogin() }

    override fun onDestroy() { view = null }
}