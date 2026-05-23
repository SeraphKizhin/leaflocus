package com.tarotcompany.leaflocus.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlantTypes(plants: List<PlantType>): List<Long>

    @Query("SELECT * FROM plant_types")
    suspend fun getAllPlantTypes(): List<PlantType>

    @Insert
    suspend fun addPlantToUserCollection(userPlant: UserPlant): Long

    @Query("""
        SELECT up.id AS plantId, pt.name, up.customNickname, up.lastWatered, pt.waterFrequency 
        FROM user_plants up 
        INNER JOIN plant_types pt ON up.plantTypeId = pt.id 
        WHERE up.userId = :userId
    """)
    suspend fun getUserPlants(userId: Int): List<UserPlantDetails>

    @Query("UPDATE user_plants SET lastWatered = :currentTime WHERE id = :plantId")
    suspend fun updateWateringTime(plantId: Int, currentTime: Long): Int

    @Query("SELECT * FROM user_plants WHERE userId = :userId AND isShowcased = 1")
    suspend fun getShowcasedPlants(userId: Int): List<UserPlant>

    @Query("SELECT COUNT(*) FROM user_plants WHERE userId = :userId")
    suspend fun getPlantCount(userId: Int): Int

    @Query("UPDATE user_plants SET isShowcased = :isShown WHERE id = :plantId")
    suspend fun updateShowcaseStatus(plantId: Int, isShown: Boolean): Int

    @Query("SELECT * FROM user_plants WHERE userId = :userId")
    suspend fun getAllRawUserPlants(userId: Int): List<UserPlant>
}