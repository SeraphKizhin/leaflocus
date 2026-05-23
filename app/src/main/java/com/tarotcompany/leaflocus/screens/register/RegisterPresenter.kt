package com.tarotcompany.leaflocus.screens.register

import com.tarotcompany.leaflocus.data.AchievementDao
import com.tarotcompany.leaflocus.data.AchievementManager
import com.tarotcompany.leaflocus.data.User
import com.tarotcompany.leaflocus.data.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterPresenter(
    private var view: RegisterContract.View?,
    private val userDao: UserDao,
    private val achievementDao: AchievementDao
) : RegisterContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun register(username: String, email: String, pass: String) {
        view?.showLoading()

        scope.launch {
            val user = User(username = username, email = email, passwordHash = pass)
            val userId = withContext(Dispatchers.IO) { userDao.insertUser(user) }

            if (userId > 0) {
                // Award the Welcome achievement
                AchievementManager.checkAndAward(
                    userId.toInt(),
                    "Welcome to LeafLocus",
                    "You've started your journey!",
                    achievementDao
                )

                view?.showRegisterSuccess()
            } else {
                view?.showRegisterError("Failed to register user.")
            }
        }
    }

    override fun onDestroy() { view = null }
}