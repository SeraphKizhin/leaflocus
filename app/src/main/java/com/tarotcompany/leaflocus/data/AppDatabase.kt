package com.tarotcompany.leaflocus.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        PlantType::class,
        UserPlant::class,
        Achievement::class,
        Friendship::class
    ],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun plantDao(): PlantDao
    abstract fun achievementDao(): AchievementDao
    abstract fun friendDao(): FriendDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val initialPlants = listOf(
            PlantType(
                name = "Dragonfruit (Pitaya)",
                scientificName = "Hylocereus undatus",
                description = "A climbing cactus that produces vibrant, sweet fruit. Requires a sturdy trellis for support as it grows.",
                lightRequirements = "Full sun (6-8 hours daily)",
                waterFrequency = "Every 2 weeks (allow soil to dry out completely between waterings)",
                soilType = "Sandy, well-draining cactus/citrus potting mix",
                idealTemperature = "65°F - 85°F (18°C - 29°C)",
                growthDifficulty = "Moderate"
            ),
            PlantType(
                name = "Monstera",
                scientificName = "Monstera deliciosa",
                description = "A popular tropical houseplant famous for its large, fenestrated (hole-filled) leaves.",
                lightRequirements = "Bright, indirect sunlight",
                waterFrequency = "Every 1-2 weeks (water when the top 2 inches of soil are dry)",
                soilType = "Peat-based potting soil with perlite for aeration",
                idealTemperature = "60°F - 80°F (15°C - 27°C)",
                growthDifficulty = "Easy"
            ),
            PlantType(
                name = "Sweet Basil",
                scientificName = "Ocimum basilicum",
                description = "A highly fragrant culinary herb used widely in cooking. Prune the top leaves often to encourage bushy growth.",
                lightRequirements = "Full sun (6+ hours daily)",
                waterFrequency = "Every 3-4 days (keep soil consistently moist but not soggy)",
                soilType = "Rich, moist, well-draining soil",
                idealTemperature = "70°F - 85°F (21°C - 29°C)",
                growthDifficulty = "Easy"
            )
        )

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "leaflocus_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}