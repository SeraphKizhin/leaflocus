package com.tarotcompany.leaflocus.screens.profile

import com.tarotcompany.leaflocus.data.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilePresenter(
    private var view: ProfileContract.View?,
    private val userDao: UserDao
) : ProfileContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun fetchProfileData(username: String) {
        scope.launch {
            // Fetch user from Room on a background thread
            val user = withContext(Dispatchers.IO) {
                userDao.getUserByUsername(username)
            }

            // If user is found, update the View on the main thread
            if (user != null) {
                view?.showUserDetails(user.username, user.email)
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