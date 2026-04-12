package com.tarotcompany.leaflocus.screens.profile

interface ProfileContract {
    interface View {
        fun showUserDetails(email: String, joinDate: String)
        fun navigateBack()
    }

    interface Presenter {
        fun fetchProfileData()
        fun onBackClicked()
    }
}