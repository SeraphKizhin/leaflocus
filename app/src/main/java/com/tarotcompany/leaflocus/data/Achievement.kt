package com.tarotcompany.leaflocus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val title: String,
    val description: String,
    val iconResId: Int, // Resource ID for an icon
    val earnedDate: Long
)