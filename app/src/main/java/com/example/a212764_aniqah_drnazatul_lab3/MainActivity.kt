package com.example.a212764_aniqah_drnazatul_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import com.example.a212764_aniqah_drnazatul_lab3.ui.theme.A212764_Aniqah_DrNazatul_Lab3Theme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A212764_Aniqah_DrNazatul_Lab3Theme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        // NAVBAR DI SINI: Supaya dia sentiasa ada kat bawah skrin
        bottomBar = { BottomNavBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "TrailFinder",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(text = searchText, onTextChange = { searchText = it })
            Spacer(modifier = Modifier.height(16.dp))
            CategoryChips()
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Recommended for you",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))
            if (searchText.isEmpty() || "datuk".contains(searchText.lowercase())) {

                TrailFullCard(
                    imageRes = R.drawable.datuk,
                    title = "Gunung Datuk",
                    location = "Rembau, Negeri Sembilan",
                    length = "4.7km",
                    elevation = "586m",
                    time = "3.5hr",
                    rating = "4.6",
                    level = "Moderate"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (searchText.isEmpty() || "rompin".contains(searchText.lowercase())) {

                TrailFullCard(
                    imageRes = R.drawable.rompin,
                    title = "Taman Negeri Rompin",
                    location = "Kuala Rompin, Pahang",
                    length = "31.5km",
                    elevation = "1,048m",
                    time = "10hr",
                    rating = "4.8",
                    level = "Hard"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SearchBar(text: String, onTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text("Search forest, hills...") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50.dp),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            Button(
                onClick = { /* Action */ },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.padding(end = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text("Search")
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun CategoryChips() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChipItem("Biking", R.drawable.biking)
        ChipItem("Running", R.drawable.running)
        ChipItem("Hiking", R.drawable.hiking)
    }
}

@Composable
fun ChipItem(text: String, iconRes: Int) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}

@Composable
fun TrailFullCard(
    imageRes: Int, title: String, location: String, length: String,
    elevation: String, time: String, rating: String, level: String
) {
    var expanded by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(400)), // Task 3: Animation
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.height(180.dp).fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                // Grouping Buttons (Favorite & Expand)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                ) {
                    IconButton(onClick = { isSaved = !isSaved }) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isSaved) Color.Red else Color.White
                        )
                    }
                    // BUTANG BARU UNTUK EXPAND
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // SEBELAH KIRI: Tajuk & Lokasi
                    Column(modifier = Modifier.weight(1f)) {
                        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }

                    // SEBELAH KANAN: Level Badge + Rating (Di Bawah)
                    Surface(
                        color = when (level) {
                            "Hard" -> Color(0xFFBC4749) // Muted Red (Terracotta)
                            "Moderate" -> MaterialTheme.colorScheme.secondary // Brown-Tan
                            else -> Color(0xFF6A994E) // Muted Forest Green
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            level, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold
                        )
                    }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("⭐ $rating", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                }

                // ANIMATION: Hanya muncul bila butang arrow ditekan
                AnimatedVisibility(visible = expanded) {
                    Column(modifier = Modifier.padding(top = 16.dp)) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // Paparan Stats (Time, Length, Elev)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InfoItem("DISTANCE", length)
                            InfoItem("ELEVATION", elevation)
                            InfoItem("EST. TIME", time)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { /* Detail */ },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("View Trail Details")
                        }
                    }
                }
            }
        }
    }

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(label, fontSize = 10.sp, color = Color.Gray)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BottomNavBar() {
    var selectedIndex by remember { mutableStateOf(0) }

    // List ikon standard dari Material Design
    val items = listOf(
        "Explore" to Icons.Default.Search,
        "Saved" to Icons.Default.Favorite,
        "Map" to Icons.Default.LocationOn,
        "Activity" to Icons.Default.List,
        "Profile" to Icons.Default.Person
    )

    // Gunakan NavigationBar (Komponen M3 yang paling standard)
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { selectedIndex = index },
                label = {
                    Text(
                        text = item.first,
                        fontSize = 10.sp,
                        fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.first,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                )
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun HomeLightPreview() {
    A212764_Aniqah_DrNazatul_Lab3Theme(
        darkTheme = false
    ) {
        HomeScreen()
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeDarkPreview() {
    A212764_Aniqah_DrNazatul_Lab3Theme(
        darkTheme = true
    ) {
        HomeScreen()
    }
}