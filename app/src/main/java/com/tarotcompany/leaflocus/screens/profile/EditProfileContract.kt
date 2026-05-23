package com.tarotcompany.leaflocus.screens.profile

import com.tarotcompany.leaflocus.data.UserPlant

interface EditProfileContract {
    interface View {
        fun showCurrentBio(bio: String)
        fun showUserPlants(plants: List<UserPlant>)
        fun showMessage(message: String)
        fun closeScreen()
    }

    interface Presenter {
        fun loadUserData(username: String)
        fun saveChanges(userId: Int, newBio: String, plantStates: Map<Int, Boolean>)
        fun onDestroy()
    }
}