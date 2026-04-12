package com.tarotcompany.leaflocus.screens.dashboard

interface DashboardContract {
    interface View {
        fun displayWelcomeMessage(username: String)
        fun navigateToProfile()
        fun navigateToLogin()
    }

    interface Presenter {
        fun loadUserData(intentUsername: String?)
        fun onProfileClicked()
        fun onLogoutClicked()
    }
}