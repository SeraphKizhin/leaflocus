package com.tarotcompany.leaflocus.data

data class UserPlantDetails(
    val plantId: Int, // The ID from the user_plants table
    val name: String, // The species name from plant_types
    val customNickname: String,
    val lastWatered: Long,
    val waterFrequency: String
)