package com.tarotcompany.leaflocus.screens.profile

import com.tarotcompany.leaflocus.data.Achievement
import com.tarotcompany.leaflocus.data.User
import com.tarotcompany.leaflocus.data.UserPlant

interface ProfileContract {
    interface View {
        fun showProfile(user: User, showcased: List<UserPlant>, achievements: List<Achievement>)
        fun showMessage(message: String)
        fun navigateBack()
    }

    interface Presenter {
        fun fetchProfileData(username: String)
        fun onBackClicked()
    }
}