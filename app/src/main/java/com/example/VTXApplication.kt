package com.example

import android.app.Application
import com.example.data.local.VTXDatabase
import com.example.data.repository.VTXRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class VTXApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database by lazy { VTXDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {
        VTXRepository(
            database.scriptDao(),
            database.redeemCodeDao(),
            database.claimedHistoryDao()
        )
    }
}
