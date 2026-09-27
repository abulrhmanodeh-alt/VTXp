package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.ClaimedHistoryEntity
import com.example.ui.RedeemUiState
import com.example.ui.theme.VtxBorder
import com.example.ui.theme.VtxBorderGlowing
import com.example.ui.theme.VtxCodeBg
import com.example.ui.theme.VtxDangerRed
import com.example.ui.theme.VtxPrimary
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
fun RedeemScreen(
    redeemState: RedeemUiState,
    claimedHistory: List<ClaimedHistoryEntity>,
    onCodeInputChange: (String) -> Unit,
    onRedeemClick: () -> Unit,
    onDismissResult: () -> Unit,
    onCopyContent: (String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Redeem Header Box
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VtxSurfaceVariant),
                border = BorderStroke(1.dp, VtxBorderGlowing),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Glowing Key Icon
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.radialGradient(
                                    listOf(VtxPrimary, Color(0xFF6D28D9))
                                )
                            )
                            .border(1.dp, Color(0xFFE9D5FF), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = "استلام الأكواد",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "نظام استلام الأكواد الحصرية",
                        color = VtxTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "ضع الكود المخصص الذي حصلت عليه واضغط استلام لتحصل على السكربت فوراً",
                        color = VtxTextSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )

                    // Code Input Field
                    OutlinedTextField(
                        value = redeemState.codeInput,
                        onValueChange = onCodeInputChange,
                        placeholder = {
                            Text("أدخل الكود هنا (مثال: VTX-VIP)", color = VtxTextMuted, fontSize = 14.sp)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = null,
                                tint = VtxPrimaryLight
                            )
                        },
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
                            .testTag("redeem_code_input")
                    )

                    // Error Message
                    if (redeemState.errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "⚠️ ${redeemState.errorMessage}",
                            color = VtxDangerRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // "استلام" Button
                    Button(
                        onClick = onRedeemClick,
                        enabled = !redeemState.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VtxPrimary,
                            contentColor = Color.White,
                            disabledContainerColor = VtxSurfaceVariant
                        ),
                        shape = RoundedCornerShape(14.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("redeem_action_button")
                    ) {
                        if (redeemState.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Redeem,
                                contentDescription = "استلام",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "استلام",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    // Sample codes hint
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "جرّب كود ترحيبي تجريبي: ",
                            color = VtxTextMuted,
                            fontSize = 11.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(VtxSurface)
                                .border(1.dp, VtxBorder, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "VTX-VIP",
                                color = VtxPrimaryLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }

        // Result Card (when a code is successfully redeemed!)
        if (redeemState.redeemedRawContent != null) {
            val (title, content) = redeemState.redeemedRawContent
            item {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF151D18)),
                        border = BorderStroke(1.5.dp, VtxTakeGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(10.dp, RoundedCornerShape(18.dp))
                            .testTag("redeemed_result_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = VtxTakeGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "تم استلام السكربت بنجاح!",
                                        color = VtxTakeGreen,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                IconButton(onClick = onDismissResult) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "إغلاق",
                                        tint = VtxTextMuted
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = title,
                                color = VtxTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Script content preview box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(VtxCodeBg)
                                    .border(1.dp, Color(0xFF1E3A2B), RoundedCornerShape(10.dp))
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = content,
                                    color = Color(0xFF6EE7B7),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    maxLines = 5
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Action button: copy again
                            Button(
                                onClick = { onCopyContent(content, title) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = VtxTakeGreen,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .testTag("copy_redeemed_script_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "نسخ السكربت",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "نسخ السكربت مجدداً",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Claimed Scripts History Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = null,
                    tint = VtxPrimaryLight,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "سجل السكربتات المستلمة (${claimedHistory.size})",
                    color = VtxTextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (claimedHistory.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VtxSurface),
                    border = BorderStroke(1.dp, VtxBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "لم تستلم أي سكربت بعد",
                            color = VtxTextMuted,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "عند كتابة الكود والضغط على استلام سيظهر سكربتك هنا فوراً",
                            color = VtxTextMuted,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(claimedHistory, key = { it.id }) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = VtxSurfaceVariant),
                    border = BorderStroke(1.dp, VtxBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(VtxSurface)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = item.code,
                                        color = VtxPrimaryLight,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = item.scriptTitle,
                                    color = VtxTextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.scriptContent,
                                color = VtxTextSecondary,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Green copy button
                        Button(
                            onClick = { onCopyContent(item.scriptContent, item.scriptTitle) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = VtxTakeGreen,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("أخذ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
