package com.tarotcompany.leaflocus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uid: String,
    val username: String,
    val email: String,
    val passwordHash: String,
    val bio: String = "Welcome to my Locus!",
    val level: Int = 1,
    val experience: Int = 0
)