package com.example.responsi1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.responsi1.model.Team
import com.example.responsi1.repository.TeamRepository
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    private val repository = TeamRepository()
    
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team> = _team
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    fun fetchTeamInfo() {
        viewModelScope.launch {
            try {
                val response = repository.getTeamInfo()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _team.value = it
                    } ?: run {
                        _error.value = "Error: Response body is null"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    _error.value = "Error: ${response.code()} - $errorBody"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }
}