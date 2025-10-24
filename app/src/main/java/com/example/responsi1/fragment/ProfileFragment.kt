package com.example.responsi1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.responsi1.R
import com.example.responsi1.viewmodel.TeamViewModel

class ProfileFragment : Fragment() {
    private val viewModel: TeamViewModel by activityViewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    val imgCrest = view.findViewById<ImageView>(R.id.imgCrest)
    val tvName = view.findViewById<TextView>(R.id.tvTeamName)
           val tvProfileDesc = view.findViewById<TextView>(R.id.tvProfileDesc)

    // Tambahkan navigasi ke masing-masing halaman
    val clubHistoryLayout = (view as ViewGroup).findViewWithTag<View>("club_history")
    val headCoachLayout = view.findViewWithTag<View>("head_coach")
    val teamSquadLayout = view.findViewWithTag<View>("team_squad")

    clubHistoryLayout?.setOnClickListener {
              findNavController().navigate(R.id.action_profileFragment_to_historyFragment)
    }
    headCoachLayout?.setOnClickListener {
              findNavController().navigate(R.id.action_profileFragment_to_coachFragment)
    }
    teamSquadLayout?.setOnClickListener {
              findNavController().navigate(R.id.action_profileFragment_to_playerFragment)
    }
        
        viewModel.team.observe(viewLifecycleOwner) { team ->
            Glide.with(this)
                .load(team.crest)
                .into(imgCrest)

            tvName.text = team.name
            tvProfileDesc.text = "${team.name}, commonly known as Wolves, is a professional football club based in Wolverhampton, West Midlands, England. Founded in 1877, the club has a rich history and plays its home matches at Molineux Stadium."
        }
        
        viewModel.fetchTeamInfo()
    }
}