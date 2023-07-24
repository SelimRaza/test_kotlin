package com.example.mbm.outletEntry.viewModel

import androidx.lifecycle.ViewModel
import com.example.newtest.Image
import com.example.newtest.ImageDao

class OutletEntryVm(private val imageDao: ImageDao) : ViewModel() {
    private var list = imageDao.getAll().toMutableList()


    fun getList() : MutableList<Image> {
        return list
    }

    fun update(index: Int, image: Image) {
        // First update the StateList
        list[index] = list.set(index, image)
        // Then update the databse
        imageDao.update(list[index])
    }

    fun addRecord(image: Image) {
        // First add to the StateList
        list.add(image)
        // Then add to the database
        imageDao.insert(image)
    }

    suspend fun removeRecord(image: Image) {
        // First remove from the StateList
        list.remove(image)
        // Then update the database
        imageDao.delete(image)
    }
}