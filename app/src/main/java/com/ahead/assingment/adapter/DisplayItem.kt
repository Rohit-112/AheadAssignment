package com.ahead.assingment.adapter

import com.ahead.assingment.network.model.MenuItem

sealed class DisplayItem {
    data class Heading(val label: String) : DisplayItem()
    data class FeatureItem(val menuItem: MenuItem) : DisplayItem()
    data class AppsToggle(val isExpanded: Boolean) : DisplayItem()
    data class FullWidthItem(val menuItem: MenuItem) : DisplayItem()
}
