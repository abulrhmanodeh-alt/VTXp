package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.ClaimedHistoryDao
import com.example.data.local.dao.RedeemCodeDao
import com.example.data.local.dao.ScriptDao
import com.example.data.local.entity.ClaimedHistoryEntity
import com.example.data.local.entity.RedeemCodeEntity
import com.example.data.local.entity.ScriptEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ScriptEntity::class,
        RedeemCodeEntity::class,
        ClaimedHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VTXDatabase : RoomDatabase() {

    abstract fun scriptDao(): ScriptDao
    abstract fun redeemCodeDao(): RedeemCodeDao
    abstract fun claimedHistoryDao(): ClaimedHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: VTXDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): VTXDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VTXDatabase::class.java,
                    "vtx_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.scriptDao(), database.redeemCodeDao())
                    }
                }
            }
        }

        private suspend fun populateInitialData(scriptDao: ScriptDao, codeDao: RedeemCodeDao) {
            val initialScripts = listOf(
                ScriptEntity(
                    title = "VTX Hub Ultimate V4",
                    code = "loadstring(game:HttpGet('https://raw.githubusercontent.com/VTX-Team/VTXHub/main/source.lua'))()",
                    imageUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=600&q=80",
                    gameCategory = "عام / All Games",
                    description = "أقوى سكربت شامل مع واجهة VTX بنفسجية، طيران، سرعة خارقة، ESP، وتجميع تلقائي.",
                    copiesCount = 2840,
                    isFeatured = true
                ),
                ScriptEntity(
                    title = "Blox Fruits Auto-Farm VTX",
                    code = "loadstring(game:HttpGet('https://raw.githubusercontent.com/VTX-Scripts/BloxFruits/main/vtx_autofarm.lua'))()",
                    imageUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=600&q=80",
                    gameCategory = "Blox Fruits",
                    description = "تجميع لفل سريع، قتل الزعماء تلقائياً، تجميع الفواكه وتخزينها، بدون باند بنسبة 100%.",
                    copiesCount = 5912,
                    isFeatured = true
                ),
                ScriptEntity(
                    title = "VTX Speed & Infinite Jump",
                    code = "loadstring(game:HttpGet('https://raw.githubusercontent.com/VTX-Scripts/Movement/main/fly_jump.lua'))()",
                    imageUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&w=600&q=80",
                    gameCategory = "عام / All Games",
                    description = "سكربت السرعة والقفز اللانهائي مع إمكانية الطيران واختراق الجدران (Noclip).",
                    copiesCount = 1420,
                    isFeatured = false
                ),
                ScriptEntity(
                    title = "Brookhaven VTX Premium GUI",
                    code = "loadstring(game:HttpGet('https://raw.githubusercontent.com/VTX-Scripts/Brookhaven/main/gui_unlocker.lua'))()",
                    imageUrl = "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?auto=format&fit=crop&w=600&q=80",
                    gameCategory = "Brookhaven",
                    description = "فتح جميع السيارات والمنازل الفاخرة، تغيير المظهر والتحكم الكامل في السيرفر.",
                    copiesCount = 3105,
                    isFeatured = false
                )
            )
            scriptDao.insertAll(initialScripts)

            val initialCodes = listOf(
                RedeemCodeEntity(
                    code = "VTX-VIP",
                    scriptTitle = "سكربت VIP الحصري من إدارة VTX",
                    scriptContent = "loadstring(game:HttpGet('https://raw.githubusercontent.com/VTX-VIP/Exclusive/main/loader.lua'))() -- VTX Exclusive VIP Edition",
                    note = "كود ترحيبي للمشتركين المميزين",
                    claimCount = 42,
                    maxClaims = 0
                ),
                RedeemCodeEntity(
                    code = "VTX-2025",
                    scriptTitle = "حزمة VTX Cyberpunk المتطورة",
                    scriptContent = "-- [[ VTX 2025 SPECIAL RELEASE ]]\nloadstring(game:HttpGet('https://raw.githubusercontent.com/VTX-2025/Bundle/main/cyber.lua'))()",
                    note = "كود هدية خاص بمناسبة التحديث الجديد",
                    claimCount = 15,
                    maxClaims = 0
                )
            )
            codeDao.insertAll(initialCodes)
        }
    }
}
