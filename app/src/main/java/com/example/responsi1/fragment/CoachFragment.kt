package com.example.responsi1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.responsi1.R
import com.example.responsi1.viewmodel.TeamViewModel

class CoachFragment : Fragment() {
    private val viewModel: TeamViewModel by activityViewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coach, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val imgCoach = view.findViewById<ImageView>(R.id.imgCoach)
        val tvName = view.findViewById<TextView>(R.id.tvCoachName)
        val tvNationality = view.findViewById<TextView>(R.id.tvCoachNationality)
        val tvDateOfBirth = view.findViewById<TextView>(R.id.tvCoachDateOfBirth)
        
            // Gambar tetap statis dari drawable, data lain dari API
            Glide.with(this)
                .load(R.drawable.ic_coach)
                .into(imgCoach)

            viewModel.team.observe(viewLifecycleOwner) { team ->
                val coach = team.coach
                tvName.text = coach.name
                tvDateOfBirth.text = coach.dateOfBirth
                tvNationality.text = coach.nationality
            }

            viewModel.fetchTeamInfo()
    }
}