package com.tarotcompany.leaflocus.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AchievementManager {

    suspend fun checkAndAward(
        userId: Int,
        title: String,
        description: String,
        achievementDao: AchievementDao
    ) {
        withContext(Dispatchers.IO) {
            val existing = achievementDao.getAchievementsForUser(userId)
            if (existing.none { it.title == title }) {
                val newAchievement = Achievement(
                    userId = userId,
                    title = title,
                    description = description,
                    iconResId = 0,
                    earnedDate = System.currentTimeMillis()
                )
                achievementDao.insertAchievement(newAchievement)
            }
        }
    }
}