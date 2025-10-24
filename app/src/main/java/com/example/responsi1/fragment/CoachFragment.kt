package com.example.responsi1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        
        // Gambar tetap statis dari drawable
        Glide.with(this)
            .load(R.drawable.ic_coach)
            .into(imgCoach)

        // Observe data tim dari API
        viewModel.team.observe(viewLifecycleOwner) { team ->
            // Coba ambil coach dari top-level team.coach
            val coachFromTop = team.coach
            if (coachFromTop != null) {
                // Data pelatih dari API (tanpa label, langsung data)
                tvName.text = coachFromTop.name
                tvDateOfBirth.text = coachFromTop.dateOfBirth
                tvNationality.text = coachFromTop.nationality
            } else {
                // Fallback: cari coach di squad dengan role == "COACH"
                val coachFromSquad = team.squad.firstOrNull { it.role?.equals("COACH", ignoreCase = true) == true }
                if (coachFromSquad != null) {
                    tvName.text = coachFromSquad.name
                    tvDateOfBirth.text = coachFromSquad.dateOfBirth
                    tvNationality.text = coachFromSquad.nationality
                } else {
                    // Tidak ada data coach dari API
                    tvName.text = "Data pelatih tidak tersedia"
                    tvDateOfBirth.text = "-"
                    tvNationality.text = "-"
                }
            }
        }

        // Observe errors untuk feedback ke user
        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let {
                Toast.makeText(requireContext(), "API error: $it", Toast.LENGTH_LONG).show()
            }
        }

        // Fetch data dari API
        viewModel.fetchTeamInfo()
    }
}