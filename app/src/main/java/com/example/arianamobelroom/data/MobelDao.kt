package com.example.arianamobelroom.data

import androidx.room.*
import com.example.arianamobelroom.model.Mobel
import kotlinx.coroutines.flow.Flow

@Dao
interface MobelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMobel(mobel: Mobel)

    @Query("SELECT * from mobel_table")
    fun getAllMobels(): Flow<List<Mobel>>

    @Query("DELETE from mobel_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMobel(mobel: Mobel)

}