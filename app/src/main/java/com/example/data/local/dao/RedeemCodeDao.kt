package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.RedeemCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RedeemCodeDao {
    @Query("SELECT * FROM redeem_codes ORDER BY createdAt DESC")
    fun getAllCodes(): Flow<List<RedeemCodeEntity>>

    @Query("SELECT * FROM redeem_codes WHERE UPPER(code) = UPPER(:code) LIMIT 1")
    suspend fun findByCode(code: String): RedeemCodeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCode(code: RedeemCodeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(codes: List<RedeemCodeEntity>)

    @Update
    suspend fun updateCode(code: RedeemCodeEntity)

    @Delete
    suspend fun deleteCode(code: RedeemCodeEntity)

    @Query("UPDATE redeem_codes SET claimCount = claimCount + 1 WHERE id = :id")
    suspend fun incrementClaimCount(id: Long)
}
