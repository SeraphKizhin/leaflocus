package com.tarotcompany.leaflocus.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    // --- Encyclopedia Queries ---
    @Insert
    suspend fun insertPlantTypes(plants: List<PlantType>): List<Long>

    @Query("SELECT * FROM plant_types")
    suspend fun getAllPlantTypes(): List<PlantType>

    // --- User's Collection Queries ---
    @Insert
    suspend fun addPlantToUserCollection(userPlant: UserPlant): Long

    @Query("""
        SELECT pt.*, up.customNickname, up.lastWatered 
        FROM user_plants up 
        INNER JOIN plant_types pt ON up.plantTypeId = pt.id 
        WHERE up.userId = :userId
    """)
    suspend fun getUserPlants(userId: Int): List<UserPlantDetails>
    // Note: You would create a small 'UserPlantDetails' data class to hold this combined data
}