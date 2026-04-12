package com.tarotcompany.leaflocus.screens.register

interface RegisterContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showRegisterSuccess()
        fun showRegisterError(message: String)
    }

    interface Presenter {
        fun register(username: String, email: String, pass: String)
        fun onDestroy()
    }
}