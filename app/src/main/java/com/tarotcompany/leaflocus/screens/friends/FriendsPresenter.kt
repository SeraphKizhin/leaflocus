package com.tarotcompany.leaflocus.screens.friends

import com.tarotcompany.leaflocus.data.FriendDao
import com.tarotcompany.leaflocus.data.Friendship
import com.tarotcompany.leaflocus.data.User
import com.tarotcompany.leaflocus.data.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendsPresenter(
    private var view: FriendsContract.View?,
    private val userDao: UserDao,
    private val friendDao: FriendDao
) : FriendsContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)
    private var currentUserId: Int = -1

    override fun loadFriends() {
        if (currentUserId == -1) return
        scope.launch {
            val friends = withContext(Dispatchers.IO) { friendDao.getFriendsForUser(currentUserId) }
            view?.showSearchResults(friends)
        }
    }

    override fun initializeUser(username: String) {
        scope.launch {
            val user = withContext(Dispatchers.IO) { userDao.getUserByUsername(username) }
            if (user != null) {
                currentUserId = user.id
                loadFriends()
            }
        }
    }

    override fun search(query: String) {
        if (query.isBlank()) {
            view?.showSearchResults(emptyList())
            loadFriends()
            return
        }

        scope.launch {
            val results = withContext(Dispatchers.IO) { userDao.searchUsers(query) }

            val filteredResults = results.filter { it.id != currentUserId }

            view?.showSearchResults(filteredResults)

            if (filteredResults.isEmpty()) {
                view?.showMessage("No users found.")
            }
        }
    }

    override fun addFriend(targetUser: User) {
        if (currentUserId == -1) return

        scope.launch {
            // Check if they are already friends
            val isAlreadyFriend = withContext(Dispatchers.IO) {
                friendDao.isAlreadyFriend(currentUserId, targetUser.id) > 0
            }

            if (isAlreadyFriend) {
                view?.showMessage("You are already friends with ${targetUser.username}!")
            } else {
                val friendship = Friendship(userId = currentUserId, friendId = targetUser.id)
                withContext(Dispatchers.IO) { friendDao.addFriend(friendship) }

                view?.showMessage("Added ${targetUser.username} as a friend!")
            }
        }
    }

    override fun onDestroy() { view = null }
}