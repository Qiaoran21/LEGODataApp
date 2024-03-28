package com.example.legodataapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legodataapp.data.AllSet
import com.example.legodataapp.data.SetRepo
import kotlinx.coroutines.launch

class SetViewModel: ViewModel() {
    private val repository = SetRepo()

    private val legoSets = MutableLiveData<AllSet>()
    val sets: LiveData<AllSet> = legoSets

    fun fetchSets() {
        viewModelScope.launch{
            val setResult = repository.getSets()
            legoSets.value = setResult
        }
    }
}