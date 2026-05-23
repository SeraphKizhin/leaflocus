package com.tarotcompany.leaflocus.screens.profile

import com.tarotcompany.leaflocus.data.PlantDao
import com.tarotcompany.leaflocus.data.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfilePresenter(
    private var view: EditProfileContract.View?,
    private val userDao: UserDao,
    private val plantDao: PlantDao
) : EditProfileContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)
    var currentUserId: Int = -1

    override fun loadUserData(username: String) {
        scope.launch {
            val user = withContext(Dispatchers.IO) { userDao.getUserByUsername(username) }
            if (user != null) {
                currentUserId = user.id
                view?.showCurrentBio(user.bio)

                val allPlants = withContext(Dispatchers.IO) { plantDao.getAllRawUserPlants(user.id) } // FIXED
                view?.showUserPlants(allPlants)
            }
        }
    }

    override fun saveChanges(userId: Int, newBio: String, plantStates: Map<Int, Boolean>) {
        scope.launch {
            withContext(Dispatchers.IO) {
                userDao.updateUserBio(userId, newBio)

                for ((plantId, isShowcased) in plantStates) {
                    plantDao.updateShowcaseStatus(plantId, isShowcased)
                }
            }
            view?.showMessage("Profile updated!")
            view?.closeScreen()
        }
    }

    override fun onDestroy() { view = null }
}