package com.tarotcompany.leaflocus.screens.profile

class ProfilePresenter(private var view: ProfileContract.View?) : ProfileContract.Presenter {

    override fun fetchProfileData() {
        // Logic happens here (e.g., getting data from a Repository or Firebase)
        val email = "user@example.com"
        val joinDate = "January 2024"

        // Tell the view what to show
        view?.showUserDetails(email, joinDate)
    }

    override fun onBackClicked() {
        view?.navigateBack()
    }

    // Add a way to clean up reference
    fun detachView() {
        view = null
    }
}