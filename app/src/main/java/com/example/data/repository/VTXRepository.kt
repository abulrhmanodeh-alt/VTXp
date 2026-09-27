package com.example.data.repository

import com.example.data.local.dao.ClaimedHistoryDao
import com.example.data.local.dao.RedeemCodeDao
import com.example.data.local.dao.ScriptDao
import com.example.data.local.entity.ClaimedHistoryEntity
import com.example.data.local.entity.RedeemCodeEntity
import com.example.data.local.entity.ScriptEntity
import kotlinx.coroutines.flow.Flow

class VTXRepository(
    private val scriptDao: ScriptDao,
    private val redeemCodeDao: RedeemCodeDao,
    private val claimedHistoryDao: ClaimedHistoryDao
) {
    // Scripts
    val allScripts: Flow<List<ScriptEntity>> = scriptDao.getAllScripts()

    fun searchScripts(query: String): Flow<List<ScriptEntity>> =
        if (query.isBlank()) scriptDao.getAllScripts() else scriptDao.searchScripts(query)

    suspend fun insertScript(script: ScriptEntity): Long = scriptDao.insertScript(script)

    suspend fun updateScript(script: ScriptEntity) = scriptDao.updateScript(script)

    suspend fun deleteScript(script: ScriptEntity) = scriptDao.deleteScript(script)

    suspend fun incrementScriptCopies(id: Long) = scriptDao.incrementCopyCount(id)

    // Redeem Codes
    val allCodes: Flow<List<RedeemCodeEntity>> = redeemCodeDao.getAllCodes()

    suspend fun findCode(code: String): RedeemCodeEntity? = redeemCodeDao.findByCode(code.trim())

    suspend fun insertCode(code: RedeemCodeEntity): Long = redeemCodeDao.insertCode(code)

    suspend fun updateCode(code: RedeemCodeEntity) = redeemCodeDao.updateCode(code)

    suspend fun deleteCode(code: RedeemCodeEntity) = redeemCodeDao.deleteCode(code)

    suspend fun incrementCodeClaim(id: Long) = redeemCodeDao.incrementClaimCount(id)

    // Claimed History
    val claimedHistory: Flow<List<ClaimedHistoryEntity>> = claimedHistoryDao.getAllClaimed()

    suspend fun recordClaim(item: ClaimedHistoryEntity): Long = claimedHistoryDao.insertClaimed(item)

    suspend fun deleteClaimed(id: Long) = claimedHistoryDao.deleteById(id)
}
