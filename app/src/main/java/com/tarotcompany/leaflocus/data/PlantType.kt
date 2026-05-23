package com.tarotcompany.leaflocus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_types")
data class PlantType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val scientificName: String,
    val description: String,
    val lightRequirements: String,
    val waterFrequency: String,
    val soilType: String,
    val idealTemperature: String,
    val growthDifficulty: String
)