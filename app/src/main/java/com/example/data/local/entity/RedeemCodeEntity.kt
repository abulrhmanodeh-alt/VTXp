package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "redeem_codes")
data class RedeemCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val code: String, // الكود السري الذي يكتبه المستخدم للاستلام
    val scriptContent: String, // السكربت الذي يستلمه الشخص
    val scriptTitle: String, // اسم السكربت أو وصفه
    val note: String = "",
    val claimCount: Int = 0,
    val maxClaims: Int = 0, // 0 means unlimited
    val createdAt: Long = System.currentTimeMillis()
)
