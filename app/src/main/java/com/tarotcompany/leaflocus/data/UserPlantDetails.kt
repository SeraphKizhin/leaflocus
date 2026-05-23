package com.tarotcompany.leaflocus.data

import androidx.room.Embedded

data class UserPlantDetails(
    @Embedded val plantType: PlantType,
    val customNickname: String,
    val lastWatered: Long
)
