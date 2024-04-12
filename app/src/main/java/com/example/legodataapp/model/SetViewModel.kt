package com.example.legodataapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legodataapp.data.AllSet
import com.example.legodataapp.data.LegoSet
import com.example.legodataapp.data.SetRepo
import kotlinx.coroutines.launch

class SetViewModel: ViewModel() {
    private val repository = SetRepo()

    private val legoSets = MutableLiveData<AllSet>()
    val sets: LiveData<AllSet> = legoSets

    private val _wishlistItems = MutableLiveData<List<LegoSet>>()
    val wishlistItems: LiveData<List<LegoSet>> get() = _wishlistItems

    init {
        _wishlistItems.value = emptyList()
    }


    private val _myLegoListItems = MutableLiveData<List<LegoSet>>()
    val myLegoListItems: LiveData<List<LegoSet>> get() = _myLegoListItems

    init {
        _myLegoListItems.value = emptyList()
    }

    fun fetchSets() {
        viewModelScope.launch{
            val setResult = repository.getSets()
            legoSets.value = setResult
        }
    }

    fun addToWishlist(legoSet: LegoSet) {
        val currentList = _wishlistItems.value ?: emptyList()
        _wishlistItems.value = currentList + legoSet
    }

    fun removeFromWishlist(legoSet: LegoSet) {
        val currentList = _wishlistItems.value ?: emptyList()
        _wishlistItems.value = currentList - legoSet
    }

    fun addToMyLegolist(legoSet: LegoSet) {
        val currentList = _myLegoListItems.value ?: emptyList()
        _myLegoListItems.value = currentList + legoSet
    }

    fun removeFromMyLegolist(legoSet: LegoSet) {
        val currentList = _myLegoListItems.value ?: emptyList()
        _myLegoListItems.value = currentList - legoSet
    }
}