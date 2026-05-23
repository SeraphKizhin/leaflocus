package com.tarotcompany.leaflocus.screens.friends

import com.tarotcompany.leaflocus.data.User

interface FriendsContract {
    interface View {
        fun showSearchResults(users: List<User>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun initializeUser(username: String)
        fun search(query: String)
        fun addFriend(targetUser: User)
        fun onDestroy()
        fun loadFriends()
    }
}