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
    // Base URL harus berakhir dengan slash dan TIDAK berisi path resource/ID.
    // Retrofit akan menggabungkan BASE_URL + endpoint (@GET("teams/{teamId}")),
    // jadi BASE_URL harus: https://api.football-data.org/v4/
    const val BASE_URL = "https://api.football-data.org/v4/"
    
    // PENTING: Ganti dengan API token yang VALID dari https://www.football-data.org/client/register
    // Token yang benar biasanya panjangnya 32 karakter (contoh: a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6)
    // Untuk mendapatkan token GRATIS:
    // 1. Daftar di https://www.football-data.org/client/register
    // 2. Verifikasi email (cek inbox/spam)
    // 3. Login ke https://www.football-data.org/client dan copy API key
    // 4. Paste API key di bawah ini (ganti seluruh string)
    const val API_TOKEN = "271ee36885ce43b68ba70de233c321cd" // Token API yang valid
    const val WOLVES_TEAM_ID = 76 // Wolverhampton Wanderers FC ID
}

const val WOLVES_TEAM_ID = 76 // Wolverhampton Wanderers FC ID