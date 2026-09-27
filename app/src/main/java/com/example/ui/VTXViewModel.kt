package com.example.ui

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.VTXApplication
import com.example.data.local.entity.ClaimedHistoryEntity
import com.example.data.local.entity.RedeemCodeEntity
import com.example.data.local.entity.ScriptEntity
import com.example.data.repository.VTXRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class VtxTab {
    SCRIPTS, // السكربتات
    CODES,   // الأكواد
    ADMIN    // الإدارة
}

enum class AdminSubTab {
    CREATE_SCRIPT, // إنشاء سكربتات
    MANAGE_SCRIPTS, // تعديل السكربتات
    CREATE_CODE,    // إنشاء أكواد
    MANAGE_CODES    // إدارة الأكواد
}

data class RedeemUiState(
    val codeInput: String = "",
    val isLoading: Boolean = false,
    val redeemedScript: ScriptEntity? = null,
    val redeemedRawContent: Pair<String, String>? = null, // title to content
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class VTXViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VTXRepository = (application as VTXApplication).repository
    private val clipboardManager =
        application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    // Current Tab
    private val _currentTab = MutableStateFlow(VtxTab.SCRIPTS)
    val currentTab: StateFlow<VtxTab> = _currentTab.asStateFlow()

    // Search Query for scripts
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // All scripts filtered by search
    val scripts: StateFlow<List<ScriptEntity>> = combine(
        repository.allScripts,
        _searchQuery
    ) { list, query ->
        if (query.isBlank()) list
        else list.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.gameCategory.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All Redeem codes
    val allCodes: StateFlow<List<RedeemCodeEntity>> = repository.allCodes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Claimed History
    val claimedHistory: StateFlow<List<ClaimedHistoryEntity>> = repository.claimedHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI Feedback events
    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage: SharedFlow<String> = _snackBarMessage.asSharedFlow()

    // Admin Auth State
    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _adminPasswordInput = MutableStateFlow("")
    val adminPasswordInput: StateFlow<String> = _adminPasswordInput.asStateFlow()

    private val _adminPasswordError = MutableStateFlow<String?>(null)
    val adminPasswordError: StateFlow<String?> = _adminPasswordError.asStateFlow()

    private val _adminSubTab = MutableStateFlow(AdminSubTab.CREATE_SCRIPT)
    val adminSubTab: StateFlow<AdminSubTab> = _adminSubTab.asStateFlow()

    // Create Script Form State
    val newScriptTitle = MutableStateFlow("")
    val newScriptCode = MutableStateFlow("")
    val newScriptImageUrl = MutableStateFlow("")
    val newScriptCategory = MutableStateFlow("عام / All Games")
    val newScriptDescription = MutableStateFlow("")

    // Edit Script Form State
    private val _editingScript = MutableStateFlow<ScriptEntity?>(null)
    val editingScript: StateFlow<ScriptEntity?> = _editingScript.asStateFlow()

    // Create Code Form State
    val newCodeKey = MutableStateFlow("")
    val newCodeScriptContent = MutableStateFlow("")
    val newCodeScriptTitle = MutableStateFlow("")
    val newCodeNote = MutableStateFlow("")

    // Redeem State
    private val _redeemState = MutableStateFlow(RedeemUiState())
    val redeemState: StateFlow<RedeemUiState> = _redeemState.asStateFlow()

    // Selected Script for full view
    private val _selectedScript = MutableStateFlow<ScriptEntity?>(null)
    val selectedScript: StateFlow<ScriptEntity?> = _selectedScript.asStateFlow()

    fun selectTab(tab: VtxTab) {
        _currentTab.value = tab
    }

    fun setAdminSubTab(subTab: AdminSubTab) {
        _adminSubTab.value = subTab
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectScriptForView(script: ScriptEntity?) {
        _selectedScript.value = script
    }

    // Copy script action ("أخذ")
    fun copyScriptToClipboard(script: ScriptEntity) {
        viewModelScope.launch {
            val clip = ClipData.newPlainText("VTX Script - ${script.title}", script.code)
            clipboardManager.setPrimaryClip(clip)
            repository.incrementScriptCopies(script.id)
            _snackBarMessage.emit("تم نسخ السكربت بنجاح: ${script.title} 🚀")
        }
    }

    fun copyCustomText(text: String, label: String = "VTX Code") {
        viewModelScope.launch {
            val clip = ClipData.newPlainText(label, text)
            clipboardManager.setPrimaryClip(clip)
            _snackBarMessage.emit("تم النسخ إلى الحافظة بنجاح ✅")
        }
    }

    // Admin Auth
    fun updateAdminPassword(password: String) {
        _adminPasswordInput.value = password
        _adminPasswordError.value = null
    }

    fun submitAdminPassword() {
        if (_adminPasswordInput.value == "vtx131211") {
            _isAdminLoggedIn.value = true
            _adminPasswordError.value = null
            _adminPasswordInput.value = ""
            viewModelScope.launch {
                _snackBarMessage.emit("مرحباً بك في لوحة تحكم إدارة VTX 🛡️")
            }
        } else {
            _adminPasswordError.value = "كلمة المرور غير صحيحة! يرجى المحاولة مجدداً."
        }
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _adminPasswordInput.value = ""
        _adminPasswordError.value = null
        viewModelScope.launch {
            _snackBarMessage.emit("تم تسجيل الخروج من الإدارة")
        }
    }

    // Create Script (إدراج)
    fun insertScript() {
        val title = newScriptTitle.value.trim()
        val code = newScriptCode.value.trim()
        if (title.isBlank()) {
            viewModelScope.launch { _snackBarMessage.emit("يرجى إدخال اسم السكربت!") }
            return
        }
        if (code.isBlank()) {
            viewModelScope.launch { _snackBarMessage.emit("يرجى إدخال كود السكربت!") }
            return
        }

        viewModelScope.launch {
            val defaultImg = if (newScriptImageUrl.value.isBlank()) {
                "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=600&q=80"
            } else {
                newScriptImageUrl.value.trim()
            }

            val script = ScriptEntity(
                title = title,
                code = code,
                imageUrl = defaultImg,
                gameCategory = newScriptCategory.value.trim().ifBlank { "عام" },
                description = newScriptDescription.value.trim()
            )
            repository.insertScript(script)

            // Reset form
            newScriptTitle.value = ""
            newScriptCode.value = ""
            newScriptImageUrl.value = ""
            newScriptCategory.value = "عام / All Games"
            newScriptDescription.value = ""

            _snackBarMessage.emit("تم إدراج السكربت بنجاح في قسم السكربتات 🎉")
            // Switch to manage tab or scripts
            _adminSubTab.value = AdminSubTab.MANAGE_SCRIPTS
        }
    }

    // Edit Script
    fun startEditingScript(script: ScriptEntity) {
        _editingScript.value = script
    }

    fun cancelEditingScript() {
        _editingScript.value = null
    }

    fun saveEditedScript(updatedScript: ScriptEntity) {
        viewModelScope.launch {
            repository.updateScript(updatedScript)
            _editingScript.value = null
            _snackBarMessage.emit("تم تعديل السكربت بنجاح ✅")
        }
    }

    fun deleteScript(script: ScriptEntity) {
        viewModelScope.launch {
            repository.deleteScript(script)
            _snackBarMessage.emit("تم حذف السكربت: ${script.title}")
        }
    }

    // Create Redeem Code
    fun generateRandomCode() {
        val random = UUID.randomUUID().toString().substring(0, 6).uppercase()
        newCodeKey.value = "VTX-$random"
    }

    fun createRedeemCode() {
        val codeKey = newCodeKey.value.trim().uppercase()
        val scriptContent = newCodeScriptContent.value.trim()
        val title = newCodeScriptTitle.value.trim()

        if (codeKey.isBlank()) {
            viewModelScope.launch { _snackBarMessage.emit("يرجى إدخال الكود!") }
            return
        }
        if (scriptContent.isBlank()) {
            viewModelScope.launch { _snackBarMessage.emit("يرجى إدخال السكربت الذي سيستلمه الشخص!") }
            return
        }

        viewModelScope.launch {
            val codeEntity = RedeemCodeEntity(
                code = codeKey,
                scriptContent = scriptContent,
                scriptTitle = if (title.isBlank()) "سكربت كود VTX" else title,
                note = newCodeNote.value.trim()
            )
            repository.insertCode(codeEntity)

            // Reset form
            newCodeKey.value = ""
            newCodeScriptContent.value = ""
            newCodeScriptTitle.value = ""
            newCodeNote.value = ""

            _snackBarMessage.emit("تم إنشاء كود الاستلام بنجاح: $codeKey 🎁")
            _adminSubTab.value = AdminSubTab.MANAGE_CODES
        }
    }

    fun deleteRedeemCode(code: RedeemCodeEntity) {
        viewModelScope.launch {
            repository.deleteCode(code)
            _snackBarMessage.emit("تم حذف الكود: ${code.code}")
        }
    }

    // Redeem action (استلام)
    fun updateRedeemInput(input: String) {
        _redeemState.value = _redeemState.value.copy(
            codeInput = input,
            errorMessage = null,
            successMessage = null
        )
    }

    fun redeemCode() {
        val codeToSearch = _redeemState.value.codeInput.trim()
        if (codeToSearch.isBlank()) {
            _redeemState.value = _redeemState.value.copy(
                errorMessage = "يرجى كتابة الكود أولاً!"
            )
            return
        }

        viewModelScope.launch {
            _redeemState.value = _redeemState.value.copy(isLoading = true, errorMessage = null)
            val foundCode = repository.findCode(codeToSearch)

            if (foundCode != null) {
                // Increment claim count
                repository.incrementCodeClaim(foundCode.id)

                // Save to history
                repository.recordClaim(
                    ClaimedHistoryEntity(
                        code = foundCode.code,
                        scriptTitle = foundCode.scriptTitle,
                        scriptContent = foundCode.scriptContent
                    )
                )

                // Copy to clipboard automatically for convenience
                val clip = ClipData.newPlainText(foundCode.scriptTitle, foundCode.scriptContent)
                clipboardManager.setPrimaryClip(clip)

                _redeemState.value = _redeemState.value.copy(
                    isLoading = false,
                    redeemedRawContent = Pair(foundCode.scriptTitle, foundCode.scriptContent),
                    successMessage = "تهانينا! تم استلام السكربت ونسخه إلى الحافظة تلقائياً 🎉",
                    codeInput = ""
                )
                _snackBarMessage.emit("تم استلام السكربت بنجاح ونسخه إلى الحافظة! 🎁")
            } else {
                _redeemState.value = _redeemState.value.copy(
                    isLoading = false,
                    errorMessage = "الكود غير صحيح أو انتهت صلاحيته! تأكد من كتابة الكود بشكل صحيح."
                )
            }
        }
    }

    fun dismissRedeemDialog() {
        _redeemState.value = _redeemState.value.copy(
            redeemedRawContent = null,
            successMessage = null
        )
    }
}
