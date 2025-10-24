package com.example.responsi1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.responsi1.R
import com.example.responsi1.model.Player
import com.google.android.material.bottomsheet.BottomSheetDialog

class PlayerAdapter(private val context: Context) : 
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    
    private var players: List<Player> = listOf()
    
    fun setPlayers(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)
        
        // Set card color based on position (dari API)
        // Goalkeeper: Kuning, Defender: Biru, Midfielder: Hijau, Forward: Merah
        val cardColor = when {
            player.position?.contains("Goalkeeper", ignoreCase = true) == true -> android.graphics.Color.parseColor("#FFD700") // Kuning
            player.position?.contains("Defence", ignoreCase = true) == true || 
            player.position?.contains("Defender", ignoreCase = true) == true -> android.graphics.Color.parseColor("#0000FF") // Biru
            player.position?.contains("Midfield", ignoreCase = true) == true -> android.graphics.Color.parseColor("#00FF00") // Hijau
            player.position?.contains("Offence", ignoreCase = true) == true || 
            player.position?.contains("Forward", ignoreCase = true) == true ||
            player.position?.contains("Attack", ignoreCase = true) == true -> android.graphics.Color.parseColor("#FF0000") // Merah
            else -> android.graphics.Color.parseColor("#CCCCCC") // Abu-abu untuk posisi lain
        }
        
        holder.cardView.setCardBackgroundColor(cardColor)
        
        // Set click listener untuk menampilkan bottom sheet
        holder.itemView.setOnClickListener {
            showPlayerDetailBottomSheet(player)
        }
    }
    
    private fun showPlayerDetailBottomSheet(player: Player) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_player_detail, null)
        
        // Set data ke bottom sheet
        view.findViewById<TextView>(R.id.tvDetailName).text = player.name
        view.findViewById<TextView>(R.id.tvDetailDateOfBirth).text = player.dateOfBirth
        view.findViewById<TextView>(R.id.tvDetailNationality).text = player.nationality
        view.findViewById<TextView>(R.id.tvDetailPosition).text = player.position ?: "Unknown"
        
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
    
    override fun getItemCount(): Int = players.size
    
    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvPlayerName)
        private val tvNationality: TextView = itemView.findViewById(R.id.tvNationality)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        
        fun bind(player: Player) {
            tvName.text = player.name
            tvNationality.text = player.nationality
        }
    }
}