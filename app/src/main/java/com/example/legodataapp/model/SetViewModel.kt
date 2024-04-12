package com.example.legodataapp.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legodataapp.data.AllSet
import com.example.legodataapp.data.LegoSet
import com.example.legodataapp.data.SetRepo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class SetViewModel : ViewModel() {
    private val repository = SetRepo()

    private val legoSets = MutableLiveData<AllSet>()
    val sets: LiveData<AllSet> = legoSets

    fun fetchSets() {
        viewModelScope.launch {
            val setResult = repository.getSets()
            legoSets.value = setResult
        }
    }

    //Database
    private val firestore = FirebaseFirestore.getInstance()

    //Wishlist
    init {
        fetchWishlistItems()
    }

    private val _wishlistSets = MutableLiveData<List<LegoSet>>()
    val wishlistSets: LiveData<List<LegoSet>> get() = _wishlistSets

    fun addToWishlist(legoSet: LegoSet) {
        val currentList = _wishlistSets.value ?: emptyList()
        _wishlistSets.value = currentList + legoSet
    }

    fun removeFromWishlist(legoSet: LegoSet) {
        val wishListCollection = firestore.collection("Wishlist")
        wishListCollection.whereEqualTo("Set Number", legoSet.set_num)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            val currentList = _wishlistSets.value ?: emptyList()
                            _wishlistSets.value = currentList - legoSet
                        }
                }
            }
    }

    fun fetchWishlistItems() {
        firestore.collection("Wishlist").get()
            .addOnSuccessListener { documents ->
                val items = mutableListOf<LegoSet>()
                for (document in documents) {
                    val setName = document.getString("Name") ?: ""
                    val setImgUrl = document.getString("Set Image") ?: ""
                    val setNum = document.getString("Set Number") ?: ""

                    val legoSet = LegoSet(name = setName, set_img_url = setImgUrl, set_num = setNum)

                    items.add(legoSet)
                }
                _wishlistSets.value = items
            }
    }

    //MyLEGO
    init {
        fetchMyLegoItems()
    }
    private val _myLegoListItems = MutableLiveData<List<LegoSet>>()
    val myLegoListItems: LiveData<List<LegoSet>> get() = _myLegoListItems

    fun addToMyLegolist(legoSet: LegoSet) {
        val currentList = _myLegoListItems.value ?: emptyList()
        _myLegoListItems.value = currentList + legoSet
    }

    fun removeFromMyLegolist(legoSet: LegoSet) {
        val wishListCollection = firestore.collection("My Lego")
        wishListCollection.whereEqualTo("Set Number", legoSet.set_num)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            val currentList = _wishlistSets.value ?: emptyList()
                            _wishlistSets.value = currentList - legoSet
                        }
                }
            }
    }

    fun fetchMyLegoItems() {
        firestore.collection("My Lego").get()
            .addOnSuccessListener { documents ->
                val items = mutableListOf<LegoSet>()
                for (document in documents) {
                    val setName = document.getString("Name") ?: ""
                    val setImgUrl = document.getString("Set Image") ?: ""
                    val setNum = document.getString("Set Number") ?: ""

                    val legoSet = LegoSet(name = setName, set_img_url = setImgUrl, set_num = setNum)

                    items.add(legoSet)
                }
                _myLegoListItems.value = items
            }
    }
}
