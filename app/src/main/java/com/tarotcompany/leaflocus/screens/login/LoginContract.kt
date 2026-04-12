package com.tarotcompany.leaflocus.screens.login

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showLoginSuccess()
        fun showLoginError()
    }

    interface Presenter {
        fun login(username: String, password: String)
        fun onDestroy()
    }
}