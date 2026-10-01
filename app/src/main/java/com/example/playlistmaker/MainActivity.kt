package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Главный экран приложения
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаём интерфейс главного экрана
        setContent {
            MainScreen()
        }
    }
}

// Отрисовывает главное меню приложения
@Composable
fun MainScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9ECEF))
            .padding(horizontal = 20.dp)
    ) {

        // Верхняя панель с названием приложения
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(Color(0xFF3775E8))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Playlist Maker",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

        // Белая область с пунктами меню
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp
                    )
                )
                .background(Color.White)
                .(top = 8.dp)
        ) {

            // Переход на экран поиска
            MainMenuButton(
                title = "Поиск",
                icon = Icons.Default.Search,
                onClick = {
                    context.startActivity(
                        Intent(context, SearchActivity::class.java)
                    )
                }
            )

            // Пункт плейлистов
            MainMenuButton(
                title = "Плейлисты",
                icon = Icons.Default.List,
                onClick = {
                    Toast.makeText(
                        context,
                        "Нажата кнопка \"Плейлисты\"",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            // Пункт избранного
            MainMenuButton(
                title = "Избранное",
                icon = Icons.Default.FavoriteBorder,
                onClick = {
                    Toast.makeText(
                        context,
                        "Нажата кнопка \"Избранное\"",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            // Переход на экран настроек
            MainMenuButton(
                title = "Настройки",
                icon = Icons.Default.Settings,
                onClick = {
                    context.startActivity(
                        Intent(context, SettingsActivity::class.java)
                    )
                }
            )
        }
    }
}

// Универсальная кнопка главного меню с иконкой и стрелкой
@Composable
fun MainMenuButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Иконка пункта меню
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color(0xFF202124),
            modifier = Modifier.width(20.dp)
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        // Название пункта
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF202124)
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        // Стрелка перехода
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF9E9E9E),
            modifier = Modifier.width(18.dp)
        )
    }
}