package com.example.playlistmaker.domain.model

data class TrackDto(
    val id: Int,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long
)