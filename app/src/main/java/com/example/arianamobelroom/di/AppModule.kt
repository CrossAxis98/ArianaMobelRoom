package com.example.arianamobelroom.di

import android.content.Context
import androidx.room.Room
import com.example.arianamobelroom.data.MobelDao
import com.example.arianamobelroom.data.MobelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMobelDao(mobelDatabase: MobelDatabase): MobelDao = mobelDatabase.mobelDao()

    @Singleton
    @Provides
    fun provideMobelDatabase(@ApplicationContext context: Context): MobelDatabase {
        return Room.databaseBuilder(
            context,
            MobelDatabase::class.java,
            "mobel_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}