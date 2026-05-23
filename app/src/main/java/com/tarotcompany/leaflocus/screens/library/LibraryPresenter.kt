package com.tarotcompany.leaflocus.screens.library

import com.tarotcompany.leaflocus.data.AchievementDao
import com.tarotcompany.leaflocus.data.AchievementManager
import com.tarotcompany.leaflocus.data.PlantDao
import com.tarotcompany.leaflocus.data.PlantType
import com.tarotcompany.leaflocus.data.UserPlant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryPresenter(
    private var view: LibraryContract.View?,
    private val plantDao: PlantDao,
    private val achievementDao: AchievementDao
) : LibraryContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun loadEncyclopedia() {
        scope.launch {
            val plants = withContext(Dispatchers.IO) { plantDao.getAllPlantTypes() }
            view?.showPlants(plants)
        }
    }

    override fun addPlantToUser(userId: Int, plant: PlantType) {
        scope.launch {
            val newTrackedPlant = UserPlant(
                userId = userId,
                plantTypeId = plant.id,
                customNickname = plant.name,
                dateAdded = System.currentTimeMillis(),
                lastWatered = System.currentTimeMillis()
            )

            withContext(Dispatchers.IO) {
                plantDao.addPlantToUserCollection(newTrackedPlant)

                val count = plantDao.getPlantCount(userId)

                val title = when (count) {
                    1 -> "First Sprout"
                    5 -> "Small Garden"
                    10 -> "Green Thumb"
                    20 -> "Plant Collector"
                    50 -> "Botanical Master"
                    else -> null
                }

                title?.let {
                    AchievementManager.checkAndAward(userId, it, "You reached $count plants!", achievementDao)
                }
            }
            view?.showMessage("${plant.name} added to your collection!")
        }
    }

    override fun onDestroy() {
        view = null
    }
}