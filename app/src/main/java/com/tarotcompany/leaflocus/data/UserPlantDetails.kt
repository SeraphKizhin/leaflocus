package com.tarotcompany.leaflocus.data

data class UserPlantDetails(
    val plantId: Int,
    val name: String,
    val customNickname: String,
    val lastWatered: Long,
    val waterFrequency: String
)