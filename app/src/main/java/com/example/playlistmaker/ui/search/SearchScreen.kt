package com.example.playlistmaker.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.view_model.SearchState
import com.example.playlistmaker.ui.view_model.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    navigateBack: () -> Unit
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Шапка
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .size(48.dp)
                    .clickable { navigateBack() }
                    .padding(12.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.menu_search),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Поле ввода
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            value = text,
            onValueChange = { newText -> text = newText },
            placeholder = { Text(stringResource(R.string.menu_search), fontSize = 16.sp, color = Color.Gray) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            searchViewModel.searchTracks(text)
                            focusManager.clearFocus()
                        }
                    },
                    imageVector = Icons.Filled.Search,
                    tint = Color.Gray,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable {
                            text = ""
                            searchViewModel.clearSearch()
                        },
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(R.string.clear)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        // Результаты поиска
        SearchResults(
            screenState = screenState,
            modifier = Modifier.padding(top = 16.dp),
            onTrackClick = { /* Пока пусто */ }
        )
    }
}

@Composable
private fun SearchResults(
    screenState: SearchState,
    modifier: Modifier,
    onTrackClick: (Track) -> Unit
) {
    when (screenState) {
        is SearchState.Initial -> { /* Ничего не показываем */ }
        is SearchState.Searching -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is SearchState.Success -> {
            val tracks = screenState.foundList
            if (tracks.isEmpty()) {
                InformerState("Ничего не найдено")
            } else {
                LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(tracks.size) { index ->
                        TrackListItem(
                            track = tracks[index],
                            onClick = { onTrackClick(tracks[index]) }
                        )
                    }
                }
            }
        }
        is SearchState.Fail -> {
            InformerState(titleText = screenState.error)
        }
    }
}

@Composable
private fun InformerState(
    titleText: String,
    subtitleText: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = titleText, fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center)
        subtitleText?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun TrackListItem(track: Track, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Track icon",
            modifier = Modifier.size(100.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(track.trackName, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(track.artistName, fontSize = 11.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("•", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 4.dp))
                Text(track.trackTime, fontSize = 11.sp, color = Color.Gray, maxLines = 1)
            }
        }
        Icon(
            modifier = Modifier.size(24.dp).padding(4.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "Arrow",
            tint = Color.Gray
        )
    }
}