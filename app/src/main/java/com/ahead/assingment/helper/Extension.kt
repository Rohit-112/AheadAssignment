package com.ahead.assingment.helper

import com.ahead.assingment.adapter.DisplayItem
import com.ahead.assingment.network.model.MenuItem
import kotlin.math.min

fun List<MenuItem>.toDisplayItems(isAppsExpanded: Boolean): List<DisplayItem> {
    val result = mutableListOf<DisplayItem>()
    var i = 0
    while (i < size) {
        val item = this[i]

        if (item.type == 0) {
            result.add(DisplayItem.Heading(item.label))
            if (item.label.equals("APPS", ignoreCase = true)) {
                val apps = mutableListOf<MenuItem>()
                var j = i + 1
                while (j < size && this[j].type != 0) {
                    apps.add(this[j])
                    j++
                }

                val visibleCount = if (isAppsExpanded) apps.size else min(4, apps.size)
                result.addAll(apps.take(visibleCount).map { DisplayItem.FeatureItem(it) })

                if (apps.size > 4) {
                    result.add(DisplayItem.AppsToggle(isAppsExpanded))
                }

                i = j - 1
            }
        } else {
            result.add(DisplayItem.FeatureItem(item))
        }

        i++
    }
    return result
}



