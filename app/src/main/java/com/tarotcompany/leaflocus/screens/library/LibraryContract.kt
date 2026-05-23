package com.tarotcompany.leaflocus.screens.library

import com.tarotcompany.leaflocus.data.PlantType

interface LibraryContract {
    interface View {
        fun showPlants(plants: List<PlantType>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun loadEncyclopedia()
        fun addPlantToUser(userId: Int, plant: PlantType)
        fun onDestroy()
    }
}