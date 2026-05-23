package com.tarotcompany.leaflocus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friendships")
data class Friendship(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val friendId: Int
)