package com.tarotcompany.leaflocus.screens.login

import com.tarotcompany.leaflocus.data.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter(
    private var view: LoginContract.View?,
    private val userDao: UserDao // Inject DAO here
) : LoginContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun login(username: String, password: String) {
        view?.showLoading()

        scope.launch {
            val user = withContext(Dispatchers.IO) {
                userDao.authenticateUser(username, password)
            }

            view?.hideLoading()
            if (user != null) {
                view?.showLoginSuccess()
            } else {
                view?.showLoginError()
            }
        }
    }

    override fun onDestroy() { view = null }
}