package com.tarotcompany.leaflocus.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements WHERE userId = :userId")
    suspend fun getAchievementsForUser(userId: Int): List<Achievement>

    @Insert
    suspend fun insertAchievement(achievement: Achievement): Long
}