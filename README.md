## Responsi 1 Praktikum Pemrograman Mobile
# Identitas
- Nama: Muhammad Ilham Rafiqi
- NIM: H1D023112
- Shif: I-C

### Vidio Demo Aplikasi
![Demo Aplikasi](DemoAplikasi3.gif)

### Penjelasan alur data mulai dari pemanggilan ke API hingga
penyajian di layar.

1. Definisi API Service
   File: FootballService.kt
   Penjelasan:
   - Mendefinisikan endpoint API: teams/76 (ID Wolverhampton)
   - Mengirim API token melalui header X-Auth-Token
   - Menggunakan suspend karena operasi asynchronous (coroutine)
   - Return type Response<Team> untuk mendapatkan status code + data
     
2. Repository Layer
   File: TeamRepository.kt
   Penjelasan:
   - Retrofit: Library untuk HTTP request
   - OkHttpClient: Client untuk menjalankan request
   - HttpLoggingInterceptor: Mencatat semua request/response di Logcat
   - GsonConverterFactory: Mengkonversi JSON response menjadi Kotlin object
   - Repository menyediakan method getTeamInfo() yang dipanggil ViewModel
   
   Proses di Repository:
   - Retrofit membuat request: GET https://api.football-data.org/v4/teams/76
   - Menambahkan header: X-Auth-Token: 271ee36885ce43b68ba70de233c321cd
   - Mengirim request ke server
   - Menerima response JSON dari API
   - Gson otomatis parse JSON → Kotlin object Team
  
3. Model/Data Class
   File: Team.kt
   Penjelasan:
   - Data class Kotlin yang merepresentasikan struktur JSON dari API
   - Setiap property di data class harus match dengan field di JSON response
   - coach? nullable karena tidak semua response API punya coach di top-level
   - role dan position nullable karena bisa kosong dari API
   - Gson otomatis mapping JSON field → property data class

4. ViewModel Layer
   File: TeamViewModel.kt
   Penjelasan:
   - ViewModel: Menyimpan dan mengelola data yang survive configuration changes
   - LiveData: Observable data holder yang lifecycle-aware
   - viewModelScope.launch: Coroutine scope yang otomatis dibatalkan saat ViewModel destroyed
   - try-catch: Menangani error (network, parsing, dll)
   
   Flow di ViewModel:
   1. Fragment memanggil fetchTeamInfo()
   2. ViewModel launch coroutine di background thread
   3. Memanggil repository.getTeamInfo()
   4. Cek response: sukses atau error
   5. Jika sukses: set _team.value (LiveData emit data)
   6. Jika error: set _error.value (LiveData emit error)
   7. Fragment yang observe team atau error akan otomatis update UI
  
5. View Layer - Profile Fragment
   File: ProfileFragment.kt
   Penjelasan:
   - activityViewModels(): Shared ViewModel across fragments dalam satu Activity
   - observe(): Subscribe ke LiveData, otomatis update saat data berubah
   - viewLifecycleOwner: Lifecycle-aware, otomatis unsubscribe saat fragment destroyed
   - Glide: Library untuk load & cache image dari URL
   
   Flow:
   1. Fragment dibuat → onViewCreated() dipanggil
   2. Setup observer untuk viewModel.team
   3. Panggil fetchTeamInfo() → trigger API call
   4. Saat data tersedia → observer callback dipanggil
   5. Update UI dengan data baru

6. View Layer - Coach Fragment
   File: CoachFragment.kt
   Penjelasan:
   - Menggunakan shared ViewModel → data hanya di-fetch sekali
   - Fallback logic: cek team.coach dulu, kalau null cek di team.squad
   - Observe error untuk menampilkan error message via Toast

7. View Layer - Players Fragment
   Penjelasan:
   - Setup RecyclerView dengan LinearLayoutManager
   - Filter squad: ambil yang role == "PLAYER" atau role == null\
   - Pass data ke adapter via setPlayers()

8. Adapter - Player Adapter
   File: PlayerAdapter.kt
   Penjelasan:
   - onCreateViewHolder(): Inflate layout untuk setiap item
   - onBindViewHolder(): Bind data ke view, set warna card, set click listener
   - showPlayerDetailBottomSheet(): Tampilkan detail pemain dalam bottom sheet


┌──────────────────────────────────────────────────────────────┐
│                         USER ACTION                          │
│                  (Buka Fragment/Click Menu)                  │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                    VIEW (Fragment)                           │
│  - ProfileFragment / CoachFragment / PlayersFragment         │
│  - Memanggil: viewModel.fetchTeamInfo()                      │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                   VIEWMODEL                                  │
│  - TeamViewModel.fetchTeamInfo()                             │
│  - Launch coroutine di background thread                     │
│  - Memanggil: repository.getTeamInfo()                       │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                    REPOSITORY                                │
│  - TeamRepository.getTeamInfo()                              │
│  - Memanggil: footballService.getTeamInfo(76, token)         │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                  RETROFIT + API SERVICE                      │
│  - Membuat HTTP GET request                                  │
│  - URL: https://api.football-data.org/v4/teams/76            │
│  - Header: X-Auth-Token: 271ee36885ce43b68ba70de233c321cd    │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│              FOOTBALL-DATA.ORG API SERVER                    │
│  - Menerima request                                          │
│  - Validasi token                                            │
│  - Query database untuk team ID 76                           │
│  - Return JSON response                                      │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼ (JSON Response)
┌──────────────────────────────────────────────────────────────┐
│                   GSON CONVERTER                             │
│  - Parse JSON response                                       │
│  - Convert JSON → Kotlin object (Team, Player, Coach)        │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                    REPOSITORY                                │
│  - Terima Response<Team> dari Retrofit                       │
│  - Return ke ViewModel                                       │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                   VIEWMODEL                                  │
│  - Cek response.isSuccessful                                 │
│  - Jika sukses: _team.value = data                           │
│  - Jika error: _error.value = errorMessage                   │
│  - LiveData emit perubahan                                   │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                  VIEW (Fragment)                             │
│  - Observer ternotifikasi                                    │
│  - Callback dipanggil dengan data baru                       │
│  - Update UI elements (TextView, ImageView, RecyclerView)    │
└────────────────────────┬─────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│                     USER SEES DATA                           │
│  - Profile: Logo, Nama, Deskripsi klub                       │
│  - Coach: Nama, Tanggal Lahir, Negara pelatih                │
│  - Players: List pemain dengan warna sesuai posisi           │
│  - Click player → Bottom sheet detail                        │
└──────────────────────────────────────────────────────────────┘
