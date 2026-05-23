package com.tarotcompany.leaflocus.screens.profile

interface ProfileContract {
    interface View {
        fun showUserDetails(username: String, email: String)
        fun navigateBack()
    }

    interface Presenter {
        fun fetchProfileData(username: String)
        fun onBackClicked()
    }
}