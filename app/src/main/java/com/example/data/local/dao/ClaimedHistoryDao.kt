package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.ClaimedHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClaimedHistoryDao {
    @Query("SELECT * FROM claimed_history ORDER BY claimedAt DESC")
    fun getAllClaimed(): Flow<List<ClaimedHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaimed(item: ClaimedHistoryEntity): Long

    @Query("DELETE FROM claimed_history WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM claimed_history")
    suspend fun clearAll()
}
