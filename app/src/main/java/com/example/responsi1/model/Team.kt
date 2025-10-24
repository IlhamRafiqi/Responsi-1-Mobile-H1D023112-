package com.example.responsi1.model

data class Team(
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    val crest: String,
    val address: String,
    val website: String,
    val founded: Int,
    val clubColors: String,
    val venue: String,
    val squad: List<Player>,
    // Some API responses include a top-level `coach`, others include coach as part of `squad` with role == "COACH".
    // Make this nullable to be tolerant to both formats.
    val coach: Coach?
)

data class Player(
    val id: Int,
    val name: String,
    val position: String?,
    // role can be "PLAYER", "COACH", etc. Add nullable role to detect coach inside squad.
    val role: String?,
    val dateOfBirth: String,
    val nationality: String,
    val shirtNumber: Int?
)

data class Coach(
    val id: Int,
    val name: String,
    val dateOfBirth: String,
    val nationality: String
)