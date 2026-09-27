package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scripts")
data class ScriptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val code: String,
    val imageUrl: String = "",
    val gameCategory: String = "عام",
    val description: String = "",
    val copiesCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val isFeatured: Boolean = false
)
