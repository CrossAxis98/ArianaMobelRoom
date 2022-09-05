package com.example.arianamobelroom.repository

import com.example.arianamobelroom.data.MobelDao
import com.example.arianamobelroom.model.Mobel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MobelRepository @Inject constructor(private val mobelDao: MobelDao) {
    suspend fun addMobel(mobel: Mobel) = mobelDao.addMobel(mobel)
    fun getAllMobels() = mobelDao.getAllMobels().flowOn(Dispatchers.IO).conflate()
    suspend fun deleteAll() = mobelDao.deleteAll()
    suspend fun deleteMobel(mobel: Mobel) = mobelDao.deleteMobel(mobel)
}