package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.entity.ScriptEntity
import com.example.ui.components.ScriptCard
import com.example.ui.theme.VtxBorder
import com.example.ui.theme.VtxPrimary
import com.example.ui.theme.VtxPrimaryLight
import com.example.ui.theme.VtxSurface
import com.example.ui.theme.VtxSurfaceVariant
import com.example.ui.theme.VtxTakeGreen
import com.example.ui.theme.VtxTextMuted
import com.example.ui.theme.VtxTextPrimary
import com.example.ui.theme.VtxTextPurple
import com.example.ui.theme.VtxTextSecondary

@Composable
fun ScriptsScreen(
    scripts: List<ScriptEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onTakeScript: (ScriptEntity) -> Unit,
    onViewScript: (ScriptEntity) -> Unit
) {
    val categories = listOf("الكل", "عام / All Games", "Blox Fruits", "Brookhaven", "Arsenal")
    var selectedCategory by remember { mutableStateOf("الكل") }

    val filteredScripts = scripts.filter { script ->
        if (selectedCategory == "الكل") true
        else script.gameCategory.contains(selectedCategory, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Cyber Banner
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = VtxSurfaceVariant),
                border = BorderStroke(1.dp, VtxBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.vtx_hero_banner),
                        contentDescription = "VTX Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xE609070F), Color(0x9913101E), Color(0x66000000))
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(VtxPrimary)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "OFFICIAL",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "VTX SCRIPT HUB",
                                color = VtxTextPurple,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "مكتبة سكربتات VTX الاحترافية",
                            color = VtxTextPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )

                        Text(
                            text = "اضغط على زر «أخذ» الأخضر لنسخ السكربت مباشرة واستخدامه فوراً!",
                            color = VtxTakeGreen,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text("ابحث باسم السكربت أو اللعبة...", color = VtxTextMuted, fontSize = 13.sp)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "بحث",
                        tint = VtxPrimaryLight
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "مسح",
                                tint = VtxTextMuted
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VtxPrimary,
                    unfocusedBorderColor = VtxBorder,
                    focusedContainerColor = VtxSurface,
                    unfocusedContainerColor = VtxSurface,
                    focusedTextColor = VtxTextPrimary,
                    unfocusedTextColor = VtxTextPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scripts_search_input")
            )
        }

        // Category Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = {
                            Text(
                                text = category,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = VtxPrimary,
                            selectedLabelColor = Color.White,
                            containerColor = VtxSurface,
                            labelColor = VtxTextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) VtxPrimaryLight else VtxBorder
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        }

        // Section Title with count
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "السكربتات المتاحة (${filteredScripts.size})",
                    color = VtxTextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "تحديث مستمر ⚡",
                    color = VtxPrimaryLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Scripts List
        if (filteredScripts.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VtxSurfaceVariant),
                    border = BorderStroke(1.dp, VtxBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = null,
                            tint = VtxTextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "لا توجد سكربتات مطابقة للبحث",
                            color = VtxTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "يمكن للإدارة إضافة سكربتات جديدة من تبويب «الإدارة»",
                            color = VtxTextMuted,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(filteredScripts, key = { it.id }) { script ->
                ScriptCard(
                    script = script,
                    onTakeClick = { onTakeScript(script) },
                    onViewClick = { onViewScript(script) }
                )
            }
        }
    }
}
