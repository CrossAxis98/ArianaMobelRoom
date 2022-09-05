package com.example.arianamobelroom.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.arianamobelroom.converter.DateConverter
import com.example.arianamobelroom.model.Mobel

@Database(entities = [Mobel::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MobelDatabase: RoomDatabase() {
    abstract fun mobelDao(): MobelDao
}