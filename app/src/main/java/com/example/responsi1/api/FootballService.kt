package com.example.responsi1.api

import com.example.responsi1.model.Team
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FootballService {
    @GET("teams/{teamId}")
    suspend fun getTeamInfo(
        @Path("teamId") teamId: Int,
        @Header("X-Auth-Token") apiToken: String
    ): Response<Team>
}

object ApiConfig {
    const val BASE_URL = "https://api.football-data.org/v4/76"
    const val API_TOKEN = "1ee36885ce43b68ba70de233c321cd" // Replace with your actual API token
    const val WOLVES_TEAM_ID = 76 // Wolverhampton Wanderers FC ID
}