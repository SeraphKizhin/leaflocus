package com.tarotcompany.leaflocus.screens.dashboard

import com.tarotcompany.leaflocus.data.UserPlantDetails

interface DashboardContract {
    interface View {
        fun showUserPlants(plants: List<UserPlantDetails>)
        fun navigateToProfile(username: String)
        fun navigateToLibrary(userId: Int)
        fun navigateToLogin()
    }

    interface Presenter {
        fun initialize(username: String)
        fun waterPlant(plantId: Int)
        fun onProfileClicked()
        fun onLibraryClicked()
        fun onLogoutClicked()
        fun onDestroy()
    }
}