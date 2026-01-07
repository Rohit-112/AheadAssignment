package com.ahead.assingment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahead.assingment.adapter.DisplayItem
import com.ahead.assingment.helper.Resource
import com.ahead.assingment.helper.toDisplayItems
import com.ahead.assingment.network.model.MenuItem
import com.ahead.assingment.repository.NavigationRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NavigationRepository) : ViewModel() {

    private val _menuItems = MutableLiveData<Resource<List<DisplayItem>>>()
    val menuItems: LiveData<Resource<List<DisplayItem>>> = _menuItems

    private var isAppsExpanded = false
    private var allMenuItems: List<MenuItem> = emptyList()

    init {
        fetchNavigation()
    }

    fun fetchNavigation() {
        _menuItems.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = repository.getNavigation()
                allMenuItems = response.result?.menus ?: emptyList()
                _menuItems.value = Resource.Success(allMenuItems.toDisplayItems(isAppsExpanded))
            } catch (e: Exception) {
                _menuItems.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleApps() {
        isAppsExpanded = !isAppsExpanded
        _menuItems.value = Resource.Success(allMenuItems.toDisplayItems(isAppsExpanded))
    }
}
