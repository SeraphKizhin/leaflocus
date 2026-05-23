package com.tarotcompany.leaflocus.screens.register

import com.tarotcompany.leaflocus.data.User
import com.tarotcompany.leaflocus.data.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterPresenter(
    private var view: RegisterContract.View?,
    private val userDao: UserDao // Inject DAO here
) : RegisterContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun register(username: String, email: String, pass: String) {
        view?.showLoading()

        scope.launch {
            val exists = withContext(Dispatchers.IO) { userDao.checkUserExists(username) }

            if (exists > 0) {
                view?.hideLoading()
                view?.showRegisterError("Username already exists")
            } else {
                val newUser = User(username = username, email = email, passwordHash = pass)
                val result = withContext(Dispatchers.IO) { userDao.insertUser(newUser) }

                view?.hideLoading()
                if (result > 0) {
                    view?.showRegisterSuccess()
                } else {
                    view?.showRegisterError("Failed to register user")
                }
            }
        }
    }

    override fun onDestroy() { view = null }
}