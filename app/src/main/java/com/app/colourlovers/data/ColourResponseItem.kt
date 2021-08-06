package com.app.colourlovers.data

data class ColourResponseItem(
    val apiUrl: String,
    val badgeUrl: String,
    val dateCreated: String,
    val description: String,
    val hex: String,
    val hsv: Hsv,
    val id: Int,
    val imageUrl: String,
    val numComments: Int,
    val numHearts: Int,
    val numViews: Int,
    val numVotes: Int,
    val rank: Int,
    val rgb: Rgb,
    val title: String,
    val url: String,
    val userName: String,
    var liked: Boolean
)

data class Hsv(
    val hue: Int,
    val saturation: Int,
    val value: Int
)

data class Rgb(
    val blue: Int,
    val green: Int,
    val red: Int
)