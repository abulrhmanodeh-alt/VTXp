package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.VTXViewModel
import com.example.ui.VtxTab
import com.example.ui.components.ScriptDetailDialog
import com.example.ui.components.VTXBottomBar
import com.example.ui.components.VTXTopBar
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.RedeemScreen
import com.example.ui.screens.ScriptsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.VtxBackground
import com.example.ui.theme.VtxBorderGlowing
import com.example.ui.theme.VtxPrimary
import com.example.ui.theme.VtxSurfaceVariant
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel: VTXViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    VTXApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun VTXApp(viewModel: VTXViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val scripts by viewModel.scripts.collectAsStateWithLifecycle()
    val allCodes by viewModel.allCodes.collectAsStateWithLifecycle()
    val claimedHistory by viewModel.claimedHistory.collectAsStateWithLifecycle()
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsStateWithLifecycle()
    val adminPasswordInput by viewModel.adminPasswordInput.collectAsStateWithLifecycle()
    val adminPasswordError by viewModel.adminPasswordError.collectAsStateWithLifecycle()
    val adminSubTab by viewModel.adminSubTab.collectAsStateWithLifecycle()

    // Form states
    val newScriptTitle by viewModel.newScriptTitle.collectAsStateWithLifecycle()
    val newScriptCode by viewModel.newScriptCode.collectAsStateWithLifecycle()
    val newScriptImageUrl by viewModel.newScriptImageUrl.collectAsStateWithLifecycle()
    val newScriptCategory by viewModel.newScriptCategory.collectAsStateWithLifecycle()
    val newScriptDescription by viewModel.newScriptDescription.collectAsStateWithLifecycle()
    val editingScript by viewModel.editingScript.collectAsStateWithLifecycle()

    val newCodeKey by viewModel.newCodeKey.collectAsStateWithLifecycle()
    val newCodeScriptContent by viewModel.newCodeScriptContent.collectAsStateWithLifecycle()
    val newCodeScriptTitle by viewModel.newCodeScriptTitle.collectAsStateWithLifecycle()
    val newCodeNote by viewModel.newCodeNote.collectAsStateWithLifecycle()

    val redeemState by viewModel.redeemState.collectAsStateWithLifecycle()
    val selectedScript by viewModel.selectedScript.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // Collect snackbar events
    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Handle back button
    BackHandler(enabled = currentTab != VtxTab.SCRIPTS || selectedScript != null) {
        if (selectedScript != null) {
            viewModel.selectScriptForView(null)
        } else if (currentTab != VtxTab.SCRIPTS) {
            viewModel.selectTab(VtxTab.SCRIPTS)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            VTXTopBar(
                currentTab = currentTab,
                isAdminLoggedIn = isAdminLoggedIn,
                onAdminLogout = { viewModel.logoutAdmin() }
            )
        },
        bottomBar = {
            VTXBottomBar(
                currentTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = VtxSurfaceVariant,
                    contentColor = Color.White,
                    actionColor = VtxPrimary
                )
            }
        },
        containerColor = VtxBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VtxBackground)
                .padding(innerPadding)
        ) {
            when (currentTab) {
                VtxTab.SCRIPTS -> {
                    ScriptsScreen(
                        scripts = scripts,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                        onTakeScript = { viewModel.copyScriptToClipboard(it) },
                        onViewScript = { viewModel.selectScriptForView(it) }
                    )
                }

                VtxTab.CODES -> {
                    RedeemScreen(
                        redeemState = redeemState,
                        claimedHistory = claimedHistory,
                        onCodeInputChange = { viewModel.updateRedeemInput(it) },
                        onRedeemClick = { viewModel.redeemCode() },
                        onDismissResult = { viewModel.dismissRedeemDialog() },
                        onCopyContent = { content, label ->
                            viewModel.copyCustomText(content, label)
                        }
                    )
                }

                VtxTab.ADMIN -> {
                    AdminScreen(
                        isLoggedIn = isAdminLoggedIn,
                        passwordInput = adminPasswordInput,
                        passwordError = adminPasswordError,
                        onPasswordChange = { viewModel.updateAdminPassword(it) },
                        onSubmitPassword = { viewModel.submitAdminPassword() },
                        adminSubTab = adminSubTab,
                        onAdminSubTabChange = { viewModel.setAdminSubTab(it) },
                        // Script Creation
                        newScriptTitle = newScriptTitle,
                        onNewScriptTitleChange = { viewModel.newScriptTitle.value = it },
                        newScriptCode = newScriptCode,
                        onNewScriptCodeChange = { viewModel.newScriptCode.value = it },
                        newScriptImageUrl = newScriptImageUrl,
                        onNewScriptImageUrlChange = { viewModel.newScriptImageUrl.value = it },
                        newScriptCategory = newScriptCategory,
                        onNewScriptCategoryChange = { viewModel.newScriptCategory.value = it },
                        newScriptDescription = newScriptDescription,
                        onNewScriptDescriptionChange = { viewModel.newScriptDescription.value = it },
                        onInsertScriptClick = { viewModel.insertScript() },
                        // Manage scripts
                        scriptsList = scripts,
                        editingScript = editingScript,
                        onStartEditing = { viewModel.startEditingScript(it) },
                        onCancelEditing = { viewModel.cancelEditingScript() },
                        onSaveEditing = { viewModel.saveEditedScript(it) },
                        onDeleteScript = { viewModel.deleteScript(it) },
                        // Create code
                        newCodeKey = newCodeKey,
                        onNewCodeKeyChange = { viewModel.newCodeKey.value = it },
                        newCodeScriptContent = newCodeScriptContent,
                        onNewCodeScriptContentChange = { viewModel.newCodeScriptContent.value = it },
                        newCodeScriptTitle = newCodeScriptTitle,
                        onNewCodeScriptTitleChange = { viewModel.newCodeScriptTitle.value = it },
                        newCodeNote = newCodeNote,
                        onNewCodeNoteChange = { viewModel.newCodeNote.value = it },
                        onGenerateRandomCode = { viewModel.generateRandomCode() },
                        onCreateCodeClick = { viewModel.createRedeemCode() },
                        // Manage codes
                        codesList = allCodes,
                        onDeleteCode = { viewModel.deleteRedeemCode(it) },
                        onCopyText = { viewModel.copyCustomText(it, "VTX Code") }
                    )
                }
            }

            // Script Detail Dialog
            if (selectedScript != null) {
                ScriptDetailDialog(
                    script = selectedScript!!,
                    onDismiss = { viewModel.selectScriptForView(null) },
                    onTakeClick = { viewModel.copyScriptToClipboard(selectedScript!!) }
                )
            }
        }
    }
}
