package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.local.entity.ScriptEntity
import com.example.ui.VtxTab
import com.example.ui.theme.VtxBackground
import com.example.ui.theme.VtxBorder
import com.example.ui.theme.VtxBorderGlowing
import com.example.ui.theme.VtxCodeBg
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
fun VTXTopBar(
    currentTab: VtxTab,
    isAdminLoggedIn: Boolean,
    onAdminLogout: () -> Unit
) {
    Surface(
        color = VtxSurface,
        border = BorderStroke(1.dp, VtxBorder),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // VTX glowing badge
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))
                            )
                        )
                        .border(1.dp, Color(0xFFC084FC), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VTX",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "VTX SCRIPTS",
                        color = VtxTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = when (currentTab) {
                            VtxTab.SCRIPTS -> "مكتبة السكربتات الرسمية"
                            VtxTab.CODES -> "نظام استلام الأكواد"
                            VtxTab.ADMIN -> "لوحة تحكم الإدارة"
                        },
                        color = VtxPrimaryLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (currentTab == VtxTab.ADMIN && isAdminLoggedIn) {
                OutlinedButton(
                    onClick = onAdminLogout,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFF87171)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF7F1D1D)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("admin_logout_button")
                ) {
                    Text("خروج", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                // Status pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(VtxSurfaceVariant)
                        .border(1.dp, VtxBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(VtxTakeGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ONLINE",
                            color = VtxTakeGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VTXBottomBar(
    currentTab: VtxTab,
    onTabSelected: (VtxTab) -> Unit
) {
    NavigationBar(
        containerColor = VtxSurface,
        tonalElevation = 10.dp,
        modifier = Modifier.border(BorderStroke(1.dp, VtxBorder))
    ) {
        // Tab 1: السكربتات
        NavigationBarItem(
            selected = currentTab == VtxTab.SCRIPTS,
            onClick = { onTabSelected(VtxTab.SCRIPTS) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Terminal,
                    contentDescription = "السكربتات"
                )
            },
            label = {
                Text(
                    text = "السكربتات",
                    fontWeight = if (currentTab == VtxTab.SCRIPTS) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 12.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = VtxPrimaryLight,
                indicatorColor = VtxPrimary,
                unselectedIconColor = VtxTextMuted,
                unselectedTextColor = VtxTextMuted
            ),
            modifier = Modifier.testTag("tab_scripts")
        )

        // Tab 2: الأكواد
        NavigationBarItem(
            selected = currentTab == VtxTab.CODES,
            onClick = { onTabSelected(VtxTab.CODES) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = "الأكواد"
                )
            },
            label = {
                Text(
                    text = "الأكواد",
                    fontWeight = if (currentTab == VtxTab.CODES) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 12.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = VtxPrimaryLight,
                indicatorColor = VtxPrimary,
                unselectedIconColor = VtxTextMuted,
                unselectedTextColor = VtxTextMuted
            ),
            modifier = Modifier.testTag("tab_codes")
        )

        // Tab 3: الإدارة
        NavigationBarItem(
            selected = currentTab == VtxTab.ADMIN,
            onClick = { onTabSelected(VtxTab.ADMIN) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "الإدارة"
                )
            },
            label = {
                Text(
                    text = "الإدارة",
                    fontWeight = if (currentTab == VtxTab.ADMIN) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 12.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = VtxPrimaryLight,
                indicatorColor = VtxPrimary,
                unselectedIconColor = VtxTextMuted,
                unselectedTextColor = VtxTextMuted
            ),
            modifier = Modifier.testTag("tab_admin")
        )
    }
}

@Composable
fun ScriptCard(
    script: ScriptEntity,
    onTakeClick: () -> Unit,
    onViewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = VtxSurfaceVariant
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, VtxBorder),
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .testTag("script_card_${script.id}")
    ) {
        Column {
            // Script Image Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color(0xFF161226))
            ) {
                if (script.imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = script.imageUrl,
                        contentDescription = script.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Fallback banner
                    Image(
                        painter = painterResource(id = R.drawable.vtx_hero_banner),
                        contentDescription = script.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0xCC09070F), Color(0xFF13101E))
                            )
                        )
                )

                // Category pill on top right
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xDD2D1E4E))
                        .border(1.dp, VtxPrimary.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = script.gameCategory,
                        color = VtxTextPurple,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Copies badge on top left
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xBB000000))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "📋 ${script.copiesCount} أخذ",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Script details & actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = script.title,
                    color = VtxTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (script.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = script.description,
                        color = VtxTextSecondary,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Actions: Button "أخذ" (Green as requested!) + Preview button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Preview Button
                    OutlinedButton(
                        onClick = onViewClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = VtxTextPurple
                        ),
                        border = BorderStroke(1.dp, VtxBorder),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("preview_script_btn_${script.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "معاينة",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "معاينة",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Green "أخذ" Button (MANDATORY REQUIREMENT)
                    Button(
                        onClick = onTakeClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VtxTakeGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .weight(1.4f)
                            .height(44.dp)
                            .testTag("take_script_btn_${script.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "أخذ السكربت",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "أخذ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}

// Preset Images for easy selection when creating scripts
val PRESET_SCRIPT_IMAGES = listOf(
    Pair("Cyber VTX", "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=600&q=80"),
    Pair("Anime Battle", "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=600&q=80"),
    Pair("Neon Runner", "https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&w=600&q=80"),
    Pair("City Life", "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?auto=format&fit=crop&w=600&q=80"),
    Pair("Robotics", "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?auto=format&fit=crop&w=600&q=80")
)

@Composable
fun PresetImagePickerRow(
    selectedUrl: String,
    onSelectUrl: (String) -> Unit
) {
    Column {
        Text(
            text = "أو اختر صورة جاهزة سريعة:",
            color = VtxTextSecondary,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PRESET_SCRIPT_IMAGES.forEach { (label, url) ->
                val isSelected = selectedUrl == url
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) VtxPrimary else VtxBorder,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onSelectUrl(url) }
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = label,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (isSelected) Color(0x66A855F7) else Color(0x44000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
