package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.ScriptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScriptDao {
    @Query("SELECT * FROM scripts ORDER BY isFeatured DESC, createdAt DESC")
    fun getAllScripts(): Flow<List<ScriptEntity>>

    @Query("SELECT * FROM scripts WHERE id = :id")
    fun getScriptById(id: Long): Flow<ScriptEntity?>

    @Query("SELECT * FROM scripts WHERE title LIKE '%' || :query || '%' OR gameCategory LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchScripts(query: String): Flow<List<ScriptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScript(script: ScriptEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(scripts: List<ScriptEntity>)

    @Update
    suspend fun updateScript(script: ScriptEntity)

    @Delete
    suspend fun deleteScript(script: ScriptEntity)

    @Query("UPDATE scripts SET copiesCount = copiesCount + 1 WHERE id = :id")
    suspend fun incrementCopyCount(id: Long)
}
