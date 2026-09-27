package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.entity.RedeemCodeEntity
import com.example.data.local.entity.ScriptEntity
import com.example.ui.AdminSubTab
import com.example.ui.components.PresetImagePickerRow
import com.example.ui.theme.VtxBorder
import com.example.ui.theme.VtxBorderGlowing
import com.example.ui.theme.VtxCodeBg
import com.example.ui.theme.VtxDangerRed
import com.example.ui.theme.VtxPrimary
import com.example.ui.theme.VtxPrimaryDark
import com.example.ui.theme.VtxPrimaryLight
import com.example.ui.theme.VtxSurface
import com.example.ui.theme.VtxSurfaceElevated
import com.example.ui.theme.VtxSurfaceVariant
import com.example.ui.theme.VtxTakeGreen
import com.example.ui.theme.VtxTextMuted
import com.example.ui.theme.VtxTextPrimary
import com.example.ui.theme.VtxTextPurple
import com.example.ui.theme.VtxTextSecondary

@Composable
fun AdminScreen(
    isLoggedIn: Boolean,
    passwordInput: String,
    passwordError: String?,
    onPasswordChange: (String) -> Unit,
    onSubmitPassword: () -> Unit,
    adminSubTab: AdminSubTab,
    onAdminSubTabChange: (AdminSubTab) -> Unit,
    // Create script fields
    newScriptTitle: String,
    onNewScriptTitleChange: (String) -> Unit,
    newScriptCode: String,
    onNewScriptCodeChange: (String) -> Unit,
    newScriptImageUrl: String,
    onNewScriptImageUrlChange: (String) -> Unit,
    newScriptCategory: String,
    onNewScriptCategoryChange: (String) -> Unit,
    newScriptDescription: String,
    onNewScriptDescriptionChange: (String) -> Unit,
    onInsertScriptClick: () -> Unit,
    // Manage scripts
    scriptsList: List<ScriptEntity>,
    editingScript: ScriptEntity?,
    onStartEditing: (ScriptEntity) -> Unit,
    onCancelEditing: () -> Unit,
    onSaveEditing: (ScriptEntity) -> Unit,
    onDeleteScript: (ScriptEntity) -> Unit,
    // Create code fields
    newCodeKey: String,
    onNewCodeKeyChange: (String) -> Unit,
    newCodeScriptContent: String,
    onNewCodeScriptContentChange: (String) -> Unit,
    newCodeScriptTitle: String,
    onNewCodeScriptTitleChange: (String) -> Unit,
    newCodeNote: String,
    onNewCodeNoteChange: (String) -> Unit,
    onGenerateRandomCode: () -> Unit,
    onCreateCodeClick: () -> Unit,
    // Manage codes
    codesList: List<RedeemCodeEntity>,
    onDeleteCode: (RedeemCodeEntity) -> Unit,
    onCopyText: (String) -> Unit
) {
    if (!isLoggedIn) {
        // Admin Login Lock Screen
        AdminLoginLock(
            password = passwordInput,
            error = passwordError,
            onPasswordChange = onPasswordChange,
            onSubmit = onSubmitPassword
        )
    } else {
        // Logged-in Admin Dashboard
        AdminDashboard(
            adminSubTab = adminSubTab,
            onSubTabChange = onAdminSubTabChange,
            // Script creation
            newScriptTitle = newScriptTitle,
            onNewScriptTitleChange = onNewScriptTitleChange,
            newScriptCode = newScriptCode,
            onNewScriptCodeChange = onNewScriptCodeChange,
            newScriptImageUrl = newScriptImageUrl,
            onNewScriptImageUrlChange = onNewScriptImageUrlChange,
            newScriptCategory = newScriptCategory,
            onNewScriptCategoryChange = onNewScriptCategoryChange,
            newScriptDescription = newScriptDescription,
            onNewScriptDescriptionChange = onNewScriptDescriptionChange,
            onInsertScriptClick = onInsertScriptClick,
            // Manage scripts
            scriptsList = scriptsList,
            editingScript = editingScript,
            onStartEditing = onStartEditing,
            onCancelEditing = onCancelEditing,
            onSaveEditing = onSaveEditing,
            onDeleteScript = onDeleteScript,
            // Create code
            newCodeKey = newCodeKey,
            onNewCodeKeyChange = onNewCodeKeyChange,
            newCodeScriptContent = newCodeScriptContent,
            onNewCodeScriptContentChange = onNewCodeScriptContentChange,
            newCodeScriptTitle = newCodeScriptTitle,
            onNewCodeScriptTitleChange = onNewCodeScriptTitleChange,
            newCodeNote = newCodeNote,
            onNewCodeNoteChange = onNewCodeNoteChange,
            onGenerateRandomCode = onGenerateRandomCode,
            onCreateCodeClick = onCreateCodeClick,
            // Manage codes
            codesList = codesList,
            onDeleteCode = onDeleteCode,
            onCopyText = onCopyText
        )
    }
}

@Composable
private fun AdminLoginLock(
    password: String,
    error: String?,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = VtxSurfaceVariant),
            border = BorderStroke(1.dp, VtxBorderGlowing),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(22.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Lock Icon
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.radialGradient(
                                listOf(VtxPrimary, Color(0xFF581C87))
                            )
                        )
                        .border(1.dp, Color(0xFFC084FC), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "قفل الإدارة",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "بوابة الإدارة الخاصة بـ VTX",
                    color = VtxTextPrimary,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "لوحة التحكم محمية بكلمة مرور مخصصة للإدارة فقط",
                    color = VtxTextSecondary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                // Password hint banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF1B142E))
                        .border(1.dp, VtxBorder, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "كلمة المرور: ",
                            color = VtxTextMuted,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "vtx131211",
                            color = VtxPrimaryLight,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = {
                        Text("أدخل كلمة مرور الإدارة...", color = VtxTextMuted, fontSize = 13.sp)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = VtxPrimaryLight
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "إخفاء" else "إظهار",
                                tint = VtxTextMuted
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { onSubmit() }),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VtxPrimary,
                        unfocusedBorderColor = VtxBorder,
                        focusedContainerColor = VtxSurfaceElevated,
                        unfocusedContainerColor = VtxSurface,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admin_password_input")
                )

                if (error != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = VtxDangerRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onSubmit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VtxPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("admin_login_submit_btn")
                ) {
                    Text(
                        text = "تسجيل الدخول للإدارة",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminDashboard(
    adminSubTab: AdminSubTab,
    onSubTabChange: (AdminSubTab) -> Unit,
    // Script creation
    newScriptTitle: String,
    onNewScriptTitleChange: (String) -> Unit,
    newScriptCode: String,
    onNewScriptCodeChange: (String) -> Unit,
    newScriptImageUrl: String,
    onNewScriptImageUrlChange: (String) -> Unit,
    newScriptCategory: String,
    onNewScriptCategoryChange: (String) -> Unit,
    newScriptDescription: String,
    onNewScriptDescriptionChange: (String) -> Unit,
    onInsertScriptClick: () -> Unit,
    // Manage scripts
    scriptsList: List<ScriptEntity>,
    editingScript: ScriptEntity?,
    onStartEditing: (ScriptEntity) -> Unit,
    onCancelEditing: () -> Unit,
    onSaveEditing: (ScriptEntity) -> Unit,
    onDeleteScript: (ScriptEntity) -> Unit,
    // Create code
    newCodeKey: String,
    onNewCodeKeyChange: (String) -> Unit,
    newCodeScriptContent: String,
    onNewCodeScriptContentChange: (String) -> Unit,
    newCodeScriptTitle: String,
    onNewCodeScriptTitleChange: (String) -> Unit,
    newCodeNote: String,
    onNewCodeNoteChange: (String) -> Unit,
    onGenerateRandomCode: () -> Unit,
    onCreateCodeClick: () -> Unit,
    // Manage codes
    codesList: List<RedeemCodeEntity>,
    onDeleteCode: (RedeemCodeEntity) -> Unit,
    onCopyText: (String) -> Unit
) {
    val tabs = listOf(
        Pair(AdminSubTab.CREATE_SCRIPT, "إنشاء سكربتات"),
        Pair(AdminSubTab.MANAGE_SCRIPTS, "تعديل السكربتات"),
        Pair(AdminSubTab.CREATE_CODE, "إنشاء أكواد"),
        Pair(AdminSubTab.MANAGE_CODES, "إدارة الأكواد")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Sub-tabs navigation bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VtxSurface)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEach { (tab, title) ->
                val isSelected = adminSubTab == tab
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) VtxPrimary else VtxSurfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) VtxPrimaryLight else VtxBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSubTabChange(tab) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag("admin_subtab_${tab.name.lowercase()}")
                ) {
                    Text(
                        text = title,
                        color = if (isSelected) Color.White else VtxTextSecondary,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        // SubTab Content
        when (adminSubTab) {
            AdminSubTab.CREATE_SCRIPT -> {
                CreateScriptTab(
                    title = newScriptTitle,
                    onTitleChange = onNewScriptTitleChange,
                    code = newScriptCode,
                    onCodeChange = onNewScriptCodeChange,
                    imageUrl = newScriptImageUrl,
                    onImageUrlChange = onNewScriptImageUrlChange,
                    category = newScriptCategory,
                    onCategoryChange = onNewScriptCategoryChange,
                    description = newScriptDescription,
                    onDescriptionChange = onNewScriptDescriptionChange,
                    onInsertClick = onInsertScriptClick
                )
            }
            AdminSubTab.MANAGE_SCRIPTS -> {
                ManageScriptsTab(
                    scripts = scriptsList,
                    onEdit = onStartEditing,
                    onDelete = onDeleteScript
                )
            }
            AdminSubTab.CREATE_CODE -> {
                CreateCodeTab(
                    codeKey = newCodeKey,
                    onCodeKeyChange = onNewCodeKeyChange,
                    scriptContent = newCodeScriptContent,
                    onScriptContentChange = onNewCodeScriptContentChange,
                    scriptTitle = newCodeScriptTitle,
                    onScriptTitleChange = onNewCodeScriptTitleChange,
                    note = newCodeNote,
                    onNoteChange = onNewCodeNoteChange,
                    onGenerateRandom = onGenerateRandomCode,
                    onCreateClick = onCreateCodeClick
                )
            }
            AdminSubTab.MANAGE_CODES -> {
                ManageCodesTab(
                    codes = codesList,
                    onDelete = onDeleteCode,
                    onCopy = onCopyText
                )
            }
        }
    }

    // Edit Script Dialog
    if (editingScript != null) {
        EditScriptDialog(
            script = editingScript,
            onDismiss = onCancelEditing,
            onSave = onSaveEditing
        )
    }
}

// 1. Create Script Tab
@Composable
private fun CreateScriptTab(
    title: String,
    onTitleChange: (String) -> Unit,
    code: String,
    onCodeChange: (String) -> Unit,
    imageUrl: String,
    onImageUrlChange: (String) -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onInsertClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "إدراج سكربت جديد في VTX",
                color = VtxTextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "سيظهر هذا السكربت لجميع المستخدمين في صفحة «السكربتات» مع زر «أخذ»",
                color = VtxTextSecondary,
                fontSize = 12.sp
            )
        }

        // Script Title (مكان احط اسم السكربت)
        item {
            Text(
                text = "اسم السكربت *",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = { Text("مثال: Blox Fruits Auto Farm VTX", color = VtxTextMuted, fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_script_title_input")
            )
        }

        // Script Category
        item {
            Text(
                text = "فئة اللعبة / القسم",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = category,
                onValueChange = onCategoryChange,
                placeholder = { Text("مثال: Blox Fruits أو عام أو Brookhaven", color = VtxTextMuted, fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_script_category_input")
            )
        }

        // Script Image (مكان احط له صورة)
        item {
            Text(
                text = "صورة السكربت (رابط الصورة)",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = imageUrl,
                onValueChange = onImageUrlChange,
                placeholder = { Text("أدخل رابط صورة (URL) أو اختر من الجاهز بالأسفل", color = VtxTextMuted, fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_script_image_input")
            )

            Spacer(modifier = Modifier.height(8.dp))
            PresetImagePickerRow(
                selectedUrl = imageUrl,
                onSelectUrl = onImageUrlChange
            )
        }

        // Script Code (مكان احط فيه السكربت)
        item {
            Text(
                text = "كود السكربت (Lua Script Code) *",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,
                placeholder = {
                    Text(
                        "loadstring(game:HttpGet('...'))()",
                        color = VtxTextMuted,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                },
                minLines = 4,
                maxLines = 8,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_script_code_input")
            )
        }

        // Description
        item {
            Text(
                text = "وصف مختصر للسكربت (اختياري)",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                placeholder = { Text("المميزات: سرعة، تجميع تلقائي، طيران...", color = VtxTextMuted, fontSize = 13.sp) },
                maxLines = 3,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Button: إدراج (MANDATORY REQUIREMENT)
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onInsertClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VtxPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("admin_insert_script_button")
            ) {
                Icon(
                    imageVector = Icons.Default.PostAdd,
                    contentDescription = "إدراج",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "إدراج",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

// 2. Manage Scripts Tab (تعديل السكربتات)
@Composable
private fun ManageScriptsTab(
    scripts: List<ScriptEntity>,
    onEdit: (ScriptEntity) -> Unit,
    onDelete: (ScriptEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "تعديل وإدارة السكربتات (${scripts.size})",
                color = VtxTextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "يمكنك تعديل اسم أي سكربت أو صورته أو كوده أو حذفه في أي وقت",
                color = VtxTextSecondary,
                fontSize = 12.sp
            )
        }

        if (scripts.isEmpty()) {
            item {
                Text(
                    text = "لا توجد سكربتات مضافة بعد",
                    color = VtxTextMuted,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        } else {
            items(scripts, key = { it.id }) { script ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VtxSurfaceVariant),
                    border = BorderStroke(1.dp, VtxBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Thumbnail
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(VtxSurface)
                        ) {
                            if (script.imageUrl.isNotBlank()) {
                                AsyncImage(
                                    model = script.imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = script.title,
                                color = VtxTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = script.gameCategory,
                                color = VtxPrimaryLight,
                                fontSize = 11.sp
                            )
                            Text(
                                text = script.code,
                                color = VtxTextMuted,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Actions: Edit and Delete
                        IconButton(
                            onClick = { onEdit(script) },
                            modifier = Modifier.testTag("edit_script_btn_${script.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "تعديل",
                                tint = VtxPrimaryLight
                            )
                        }

                        IconButton(
                            onClick = { onDelete(script) },
                            modifier = Modifier.testTag("delete_script_btn_${script.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "حذف",
                                tint = VtxDangerRed
                            )
                        }
                    }
                }
            }
        }
    }
}

// Dialog for editing an existing script
@Composable
private fun EditScriptDialog(
    script: ScriptEntity,
    onDismiss: () -> Unit,
    onSave: (ScriptEntity) -> Unit
) {
    var title by remember { mutableStateOf(script.title) }
    var code by remember { mutableStateOf(script.code) }
    var imageUrl by remember { mutableStateOf(script.imageUrl) }
    var category by remember { mutableStateOf(script.gameCategory) }
    var description by remember { mutableStateOf(script.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("تعديل السكربت", color = VtxTextPrimary, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Name
                Text("اسم السكربت:", color = VtxTextPurple, fontSize = 12.sp)
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    singleLine = true,
                    colors = defaultFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Image URL
                Text("صورة السكربت (رابط):", color = VtxTextPurple, fontSize = 12.sp)
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    singleLine = true,
                    colors = defaultFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Presets
                PresetImagePickerRow(
                    selectedUrl = imageUrl,
                    onSelectUrl = { imageUrl = it }
                )

                // Category
                Text("الفئة / اللعبة:", color = VtxTextPurple, fontSize = 12.sp)
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    singleLine = true,
                    colors = defaultFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Code
                Text("كود السكربت:", color = VtxTextPurple, fontSize = 12.sp)
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    minLines = 3,
                    colors = defaultFieldColors(),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Description
                Text("الوصف:", color = VtxTextPurple, fontSize = 12.sp)
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    colors = defaultFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        script.copy(
                            title = title.trim(),
                            code = code.trim(),
                            imageUrl = imageUrl.trim(),
                            gameCategory = category.trim(),
                            description = description.trim()
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = VtxPrimary)
            ) {
                Text("حفظ التعديل")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("إلغاء", color = VtxTextMuted)
            }
        },
        containerColor = VtxSurface
    )
}

// 3. Create Code Tab (مكان اسمه إنشاء اكواد)
@Composable
private fun CreateCodeTab(
    codeKey: String,
    onCodeKeyChange: (String) -> Unit,
    scriptContent: String,
    onScriptContentChange: (String) -> Unit,
    scriptTitle: String,
    onScriptTitleChange: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit,
    onGenerateRandom: () -> Unit,
    onCreateClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "إنشاء كود استلام جديد",
                color = VtxTextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "ضع الكود والسكربت الذي سيستلمه الشخص عند كتابته لهذا الكود في صفحة الأكواد",
                color = VtxTextSecondary,
                fontSize = 12.sp
            )
        }

        // Code Key (مكان تحط فيه الكود)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "الكود (Code Key) *",
                    color = VtxTextPurple,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedButton(
                    onClick = onGenerateRandom,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = VtxPrimaryLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("توليد كود عشوائي", fontSize = 11.sp, color = VtxPrimaryLight)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = codeKey,
                onValueChange = onCodeKeyChange,
                placeholder = { Text("مثال: VTX-VIP2025 أو SPEED-X", color = VtxTextMuted, fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_code_key_input")
            )
        }

        // Script Title / Label
        item {
            Text(
                text = "اسم أو عنوان السكربت المستلم",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = scriptTitle,
                onValueChange = onScriptTitleChange,
                placeholder = { Text("مثال: سكربت VIP الحصري", color = VtxTextMuted, fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_code_script_title_input")
            )
        }

        // Script Content to deliver (مكان تحط فيه السكربت الي يستلمه الشخص)
        item {
            Text(
                text = "السكربت الذي يستلمه الشخص *",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = scriptContent,
                onValueChange = onScriptContentChange,
                placeholder = {
                    Text(
                        "loadstring(game:HttpGet('...'))()",
                        color = VtxTextMuted,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                },
                minLines = 4,
                maxLines = 8,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_code_script_content_input")
            )
        }

        // Note
        item {
            Text(
                text = "ملاحظة إدارية داخلية (اختياري)",
                color = VtxTextPurple,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = note,
                onValueChange = onNoteChange,
                placeholder = { Text("مخصص لمسابقة الديسكورد...", color = VtxTextMuted, fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = defaultFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Submit Button
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onCreateClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VtxPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("admin_create_code_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "إنشاء الكود",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

// 4. Manage Codes Tab (إدارة الأكواد)
@Composable
private fun ManageCodesTab(
    codes: List<RedeemCodeEntity>,
    onDelete: (RedeemCodeEntity) -> Unit,
    onCopy: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "الأكواد المفعلة (${codes.size})",
                color = VtxTextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "قائمة الأكواد التي يمكن للمستخدمين كتابتها في صفحة الأكواد لاستلام السكربتات",
                color = VtxTextSecondary,
                fontSize = 12.sp
            )
        }

        if (codes.isEmpty()) {
            item {
                Text(
                    text = "لا توجد أكواد مضافة حتى الآن",
                    color = VtxTextMuted,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        } else {
            items(codes, key = { it.id }) { codeEntity ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VtxSurfaceVariant),
                    border = BorderStroke(1.dp, VtxBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFF261A42))
                                        .border(1.dp, VtxPrimary, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = codeEntity.code,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 13.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "تم الاستلام: ${codeEntity.claimCount} مرة",
                                    color = VtxTakeGreen,
                                    fontSize = 11.sp
                                )
                            }

                            Row {
                                IconButton(onClick = { onCopy(codeEntity.code) }) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "نسخ الكود",
                                        tint = VtxPrimaryLight,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                IconButton(onClick = { onDelete(codeEntity) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "حذف الكود",
                                        tint = VtxDangerRed,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "العنوان: ${codeEntity.scriptTitle}",
                            color = VtxTextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(VtxCodeBg)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = codeEntity.scriptContent,
                                color = Color(0xFF6EE7B7),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun defaultFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = VtxPrimary,
    unfocusedBorderColor = VtxBorder,
    focusedContainerColor = VtxSurfaceElevated,
    unfocusedContainerColor = VtxSurface,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White
)
