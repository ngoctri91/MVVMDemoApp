package com.example.remote.response.playlist

data class Video(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String,
    val closedCaptions: String?) {
}