package com.tarotcompany.leaflocus.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FriendDao {
    @Insert
    suspend fun addFriend(friendship: Friendship): Long

    @Query("SELECT * FROM users INNER JOIN friendships ON users.id = friendships.friendId WHERE friendships.userId = :currentUserId")
    suspend fun getFriendsForUser(currentUserId: Int): List<User>

    @Query("SELECT COUNT(*) FROM friendships WHERE userId = :currentUserId AND friendId = :targetUserId")
    suspend fun isAlreadyFriend(currentUserId: Int, targetUserId: Int): Int
}