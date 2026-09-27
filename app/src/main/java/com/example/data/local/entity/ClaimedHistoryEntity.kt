package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "claimed_history")
data class ClaimedHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val code: String,
    val scriptTitle: String,
    val scriptContent: String,
    val claimedAt: Long = System.currentTimeMillis()
)
