package com.example.arianamobelroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arianamobelroom.model.Mobel
import com.example.arianamobelroom.repository.MobelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobelViewModel @Inject constructor(
    private val repository: MobelRepository
    ): ViewModel() {

    private val _mobelList = MutableStateFlow<List<Mobel>>(emptyList())
    val mobelList = _mobelList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMobels().distinctUntilChanged()
                .collect { listOfMobels ->
                    if (listOfMobels.isNullOrEmpty()) {
                        _mobelList.value = emptyList()
                    } else {
                        _mobelList.value = listOfMobels
                    }
                }
        }
    }

    fun addMobel(mobel: Mobel) = viewModelScope.launch { repository.addMobel(mobel) }
    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }
    fun deleteMobel(mobel: Mobel) = viewModelScope.launch { repository.deleteMobel(mobel) }
}