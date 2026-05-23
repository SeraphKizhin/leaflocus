package com.tarotcompany.leaflocus.screens.library

import com.tarotcompany.leaflocus.data.PlantDao
import com.tarotcompany.leaflocus.data.PlantType
import com.tarotcompany.leaflocus.data.UserPlant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryPresenter(
    private var view: LibraryContract.View?,
    private val plantDao: PlantDao
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
            // Create a new tracking entry for this user
            val newTrackedPlant = UserPlant(
                userId = userId,
                plantTypeId = plant.id,
                customNickname = plant.name, // Default to the species name
                dateAdded = System.currentTimeMillis(),
                lastWatered = System.currentTimeMillis()
            )

            withContext(Dispatchers.IO) {
                plantDao.addPlantToUserCollection(newTrackedPlant)
            }

            view?.showMessage("${plant.name} added to your collection!")
        }
    }

    override fun onDestroy() {
        view = null
    }
}