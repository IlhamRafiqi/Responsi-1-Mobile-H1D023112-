package com.example.responsi1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.responsi1.R
import com.example.responsi1.model.Player

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
        
        // Set card color based on position
        val cardColor = when(player.position) {
            "Goalkeeper" -> R.color.goalkeeper_color
            "Defender" -> R.color.defender_color
            "Midfielder" -> R.color.midfielder_color
            "Offence" -> R.color.forward_color
            else -> R.color.default_color
        }
        
        holder.cardView.setCardBackgroundColor(
            ContextCompat.getColor(context, cardColor)
        )
    }
    
    override fun getItemCount(): Int = players.size
    
    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvPlayerName)
        private val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        private val tvNationality: TextView = itemView.findViewById(R.id.tvNationality)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        
        fun bind(player: Player) {
            tvName.text = player.name
            tvPosition.text = player.position
            tvNationality.text = player.nationality
        }
    }
}