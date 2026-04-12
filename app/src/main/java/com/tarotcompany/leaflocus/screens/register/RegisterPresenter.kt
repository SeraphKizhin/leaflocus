package com.tarotcompany.leaflocus.screens.register

class RegisterPresenter(private var view: RegisterContract.View?) : RegisterContract.Presenter {
    override fun register(username: String, email: String, pass: String) {
        view?.showLoading()
        // Logic to save user to Repository
        // If success:
        view?.hideLoading()
        view?.showRegisterSuccess()
    }

    override fun onDestroy() { view = null }
}