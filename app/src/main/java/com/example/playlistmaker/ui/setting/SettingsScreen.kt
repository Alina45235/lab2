package com.example.playlistmaker.ui.setting

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R

@Composable
fun SettingsScreen(onBackClick: () -> Unit = {}) {
    // Контекст нужен для запуска Intent (системных действий)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Шапка: стрелка "назад" + заголовок "Настройки"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onBackClick() } // возврат на предыдущий экран
                    .padding(12.dp)
            )
            Text(
                text = stringResource(R.string.settings_title),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Состояние переключателя тёмной темы (логика пока не реализована)
        var darkTheme by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(16.dp)) // <-- Добавлен отступ
        // Пункт "Тёмная тема" со свитчем
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.settings_dark_theme),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = darkTheme,
                onCheckedChange = { darkTheme = it }
            )
        }

        // Пункт "Поделиться приложением" — открывает диалог выбора мессенджера
        SettingsItem(
            title = stringResource(R.string.settings_share_app),
            trailingIcon = Icons.Filled.Share,
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.settings_share_app_link))
                }
                context.startActivity(Intent.createChooser(shareIntent, null))
            }
        )

        // Пункт "Написать в поддержку" — открывает почтовый клиент с предзаполненными полями
        SettingsItem(
            title = stringResource(R.string.settings_write_support),
            trailingIcon = Icons.Filled.SupportAgent,
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.settings_support_email)))
                    putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.settings_support_subject))
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.settings_support_body))
                }
                context.startActivity(emailIntent)
            }
        )

        // Пункт "Пользовательское соглашение" — открывает браузер по ссылке
        SettingsItem(
            title = stringResource(R.string.settings_user_agreement),
            trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = {
                val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(context.getString(R.string.settings_offer_link))
                }
                context.startActivity(browserIntent)
            }
        )
    }
}

// Переиспользуемый элемент списка настроек: текст + иконка справа
@Composable
fun SettingsItem(
    title: String,
    trailingIcon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable { onClick() } // вся строка кликабельна
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f) // текст занимает всё свободное место
        )
        Icon(
            imageVector = trailingIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}