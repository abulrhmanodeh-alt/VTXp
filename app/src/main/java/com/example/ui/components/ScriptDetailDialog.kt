package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.data.local.entity.ScriptEntity
import com.example.ui.theme.VtxBorder
import com.example.ui.theme.VtxCodeBg
import com.example.ui.theme.VtxPrimary
import com.example.ui.theme.VtxSurface
import com.example.ui.theme.VtxSurfaceVariant
import com.example.ui.theme.VtxTakeGreen
import com.example.ui.theme.VtxTextMuted
import com.example.ui.theme.VtxTextPrimary
import com.example.ui.theme.VtxTextPurple
import com.example.ui.theme.VtxTextSecondary

@Composable
fun ScriptDetailDialog(
    script: ScriptEntity,
    onDismiss: () -> Unit,
    onTakeClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = VtxSurface),
            border = BorderStroke(1.dp, VtxBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("script_detail_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Image Header with close icon
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    if (script.imageUrl.isNotBlank()) {
                        AsyncImage(
                            model = script.imageUrl,
                            contentDescription = script.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                    }

                    // Close button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0x99000000))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "إغلاق",
                            tint = Color.White
                        )
                    }

                    // Game Category pill
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xDD3B1D6A))
                            .border(1.dp, VtxPrimary, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = script.gameCategory,
                            color = VtxTextPurple,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = script.title,
                        color = VtxTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (script.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = script.description,
                            color = VtxTextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "كود السكربت (Script Code):",
                        color = VtxPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Code Viewer Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(VtxCodeBg)
                            .border(1.dp, VtxBorder, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = script.code,
                            color = Color(0xFF34D399),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = VtxTextMuted
                            ),
                            border = BorderStroke(1.dp, VtxBorder),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                        ) {
                            Text("رجوع", fontSize = 13.sp)
                        }

                        // Take button (Green)
                        Button(
                            onClick = {
                                onTakeClick()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = VtxTakeGreen,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1.5f)
                                .height(46.dp)
                                .testTag("dialog_take_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "أخذ",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "أخذ ونسخ",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
