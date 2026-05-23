package com.tarotcompany.leaflocus.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_plants",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = PlantType::class, parentColumns = ["id"], childColumns = ["plantTypeId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class UserPlant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val plantTypeId: Int,
    val customNickname: String,
    val dateAdded: Long,
    val lastWatered: Long,
    val isShowcased: Boolean = false
)