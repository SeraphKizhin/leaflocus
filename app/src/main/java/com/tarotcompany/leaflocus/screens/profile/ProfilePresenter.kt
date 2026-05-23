package com.tarotcompany.leaflocus.screens.profile

import com.tarotcompany.leaflocus.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilePresenter(
    private var view: ProfileContract.View?,
    private val userDao: UserDao,
    private val plantDao: PlantDao,
    private val achievementDao: AchievementDao
) : ProfileContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun fetchProfileData(username: String) {
        scope.launch(Dispatchers.IO) {
            val user = userDao.getUserByUsername(username)
            if (user != null) {
                val showcased = plantDao.getShowcasedPlants(user.id)
                val achievements = achievementDao.getAchievementsForUser(user.id)

                withContext(Dispatchers.Main) {
                    view?.showProfile(user, showcased, achievements)
                }
            } else {
                withContext(Dispatchers.Main) {
                    view?.showMessage("User not found")
                }
            }
        }
    }

    override fun onBackClicked() {
        view?.navigateBack()
    }

    fun detachView() {
        view = null
    }
}