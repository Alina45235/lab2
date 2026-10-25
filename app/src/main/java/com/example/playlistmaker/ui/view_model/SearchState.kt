package com.example.playlistmaker.ui.view_model

import com.example.playlistmaker.domain.model.Track

sealed class SearchState {
    object Initial : SearchState()
    object Searching : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Fail(val error: String) : SearchState()
}