package com.tarotcompany.leaflocus.screens.login

class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter {

    override fun login(username: String, password: String) {
        view?.showLoading()
        // Logic to communicate with Model/Repository goes here
        // For now, let's just simulate a success
        if (username.isNotEmpty() && password.isNotEmpty()) {
            view?.hideLoading()
            view?.showLoginSuccess()
        } else {
            view?.hideLoading()
            view?.showLoginError()
        }
    }

    override fun onDestroy() {
        view = null // Prevent memory leaks
    }
}