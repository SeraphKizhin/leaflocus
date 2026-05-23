package com.tarotcompany.leaflocus.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND passwordHash = :password LIMIT 1")
    suspend fun authenticateUser(username: String, password: String): User?

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun checkUserExists(username: String): Int

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    // Added this helper so the Dashboard knows which user is logged in!
    @Query("SELECT id FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserId(username: String): Int?

    @Query("UPDATE users SET bio = :newBio WHERE id = :userId")
    suspend fun updateUserBio(userId: Int, newBio: String): Int
}