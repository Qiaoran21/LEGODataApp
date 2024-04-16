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

    //Database
    private val firestore = FirebaseFirestore.getInstance()

    private val _wishlistSets = MutableLiveData<List<LegoSet>>()
    val wishlistSets: LiveData<List<LegoSet>> get() = _wishlistSets

    private val authViewModel = AuthViewModel()

    //Wishlist
    init {
        authViewModel.user.observeForever { user ->
            user?.let {
                fetchWishlistItems(user.id)
            }
        }
    }

    fun updateUserId(userId: String){
        _wishlistSets.value = emptyList()
        _myLegoListItems.value = emptyList()
        fetchWishlistItems(userId)
        fetchMyLegoItems(userId)
    }

    fun fetchSets() {
        viewModelScope.launch {
            val setResult = repository.getSets()
            legoSets.value = setResult
        }
    }

    fun addToWishlist(legoSet: LegoSet, userId: String) {
        val currentList = _wishlistSets.value ?: emptyList()
        _wishlistSets.value = currentList + legoSet
    }

    fun removeFromWishlist(legoSet: LegoSet, userId: String) {
        val wishlistCollection = firestore.collection("Users")
            .document(userId)
            .collection("Wishlist")
        wishlistCollection.whereEqualTo("set_num", legoSet.set_num)
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

    fun fetchWishlistItems(userId: String) {
        try {
            val wishlistCollection = firestore.collection("Users")
                .document(userId)
                .collection("Wishlist")

            wishlistCollection.get()
                .addOnSuccessListener { documents ->
                    val items = mutableListOf<LegoSet>()
                    for (document in documents) {
                        val setName = document.getString("name") ?: ""
                        val setImgUrl = document.getString("set_img_url") ?: ""
                        val setNum = document.getString("set_num") ?: ""
                        val year = document.getLong("year")?.toInt() ?: 0
                        val last_modified_dt = document.getString("last_modified_dt") ?: ""
                        val num_parts = document.getLong("num_parts")?.toInt() ?: 0
                        val set_url = document.getString("set_url") ?: ""
                        val theme_id = document.getLong("theme_id")?.toInt() ?: 0

                        val legoSet = LegoSet(
                            last_modified_dt = last_modified_dt,
                            name = setName,
                            num_parts = num_parts,
                            set_img_url = setImgUrl,
                            set_num = setNum,
                            set_url = set_url,
                            theme_id = theme_id,
                            year = year)

                        items.add(legoSet)
                    }
                    _wishlistSets.value = items
                }
        }catch (e: Exception){

        }

    }

    //MyLEGO
    init {
        authViewModel.user.observeForever { user ->
            user?.let {
                fetchMyLegoItems(user.id)
            }
        }
    }
    private val _myLegoListItems = MutableLiveData<List<LegoSet>>()
    val myLegoListItems: LiveData<List<LegoSet>> get() = _myLegoListItems

    fun addToMyLegolist(legoSet: LegoSet) {
        val currentList = _myLegoListItems.value ?: emptyList()
        _myLegoListItems.value = currentList + legoSet
    }

    fun removeFromMyLegolist(legoSet: LegoSet, userId: String) {
        val wishlistCollection = firestore.collection("Users")
            .document(userId)
            .collection("My Lego")
        wishlistCollection.whereEqualTo("set_num", legoSet.set_num)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            val currentList = _myLegoListItems.value ?: emptyList()
                            _myLegoListItems.value = currentList - legoSet
                        }
                }
            }
    }

    fun fetchMyLegoItems(userId: String) {
        try {
            val wishlistCollection = firestore.collection("Users")
                .document(userId)
                .collection("My Lego")

            wishlistCollection.get()
                .addOnSuccessListener { documents ->
                    val items = mutableListOf<LegoSet>()
                    for (document in documents) {
                        val setName = document.getString("name") ?: ""
                        val setImgUrl = document.getString("set_img_url") ?: ""
                        val setNum = document.getString("set_num") ?: ""
                        val year = document.getLong("year")?.toInt() ?: 0
                        val last_modified_dt = document.getString("last_modified_dt") ?: ""
                        val num_parts = document.getLong("num_parts")?.toInt() ?: 0
                        val set_url = document.getString("set_url") ?: ""
                        val theme_id = document.getLong("theme_id")?.toInt() ?: 0

                        val legoSet = LegoSet(
                            last_modified_dt = last_modified_dt,
                            name = setName,
                            num_parts = num_parts,
                            set_img_url = setImgUrl,
                            set_num = setNum,
                            set_url = set_url,
                            theme_id = theme_id,
                            year = year)

                        items.add(legoSet)
                    }
                    _myLegoListItems.value = items
                }
        }catch (e: Exception){

        }
    }
}
