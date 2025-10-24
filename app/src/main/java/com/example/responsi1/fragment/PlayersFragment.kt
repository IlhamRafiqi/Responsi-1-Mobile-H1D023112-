package com.example.responsi1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.responsi1.R
import com.example.responsi1.adapter.PlayerAdapter
import com.example.responsi1.viewmodel.TeamViewModel

class PlayersFragment : Fragment() {
    private val viewModel: TeamViewModel by activityViewModels()
    private lateinit var playerAdapter: PlayerAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_players, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvPlayers)
        playerAdapter = PlayerAdapter(requireContext())
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
        }
        
        viewModel.team.observe(viewLifecycleOwner) { team ->
            playerAdapter.setPlayers(team.squad)
        }
        
        viewModel.fetchTeamInfo()
    }
}