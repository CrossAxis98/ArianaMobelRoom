package com.example.arianamobelroom.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "mobel_table", indices = [Index(value = ["mobelName"], unique = true)])
data class Mobel(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    val mobelName: String,
    val mobelCategory: String,
    val entryDate: Date = Date.from(Instant.now())
)