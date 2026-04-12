package com.tarotcompany.leaflocus.screens.dashboard

class DashboardPresenter(private var view: DashboardContract.View?) : DashboardContract.Presenter {
    override fun loadUserData(intentUsername: String?) {
        val name = intentUsername ?: "Guest"
        view?.displayWelcomeMessage(name)
    }

    override fun onProfileClicked() {
        view?.navigateToProfile()
    }

    override fun onLogoutClicked() {
        view?.navigateToLogin()
    }
}