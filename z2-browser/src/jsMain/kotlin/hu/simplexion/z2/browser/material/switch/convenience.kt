package hu.simplexion.z2.browser.material.switch

import hu.simplexion.z2.browser.browserIcons
import hu.simplexion.z2.browser.css.css
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import hu.simplexion.z2.browser.html.onClick
import hu.simplexion.z2.browser.material.icon.icon

fun Z2.switch(
    selected: Boolean,
    onChange: (on :Boolean) -> Unit
) : Z2 =
    switch(selected, selectedIcon = true, unselectedIcon = false, onChange = onChange)

fun Z2.switch(
    selected: Boolean,
    selectedIcon: Boolean = true,
    unselectedIcon: Boolean = false,
    onChange: (on : Boolean) -> Unit
) : Z2 =
    div("switch-track".css) {
        val statusClass = (if (selected) "selected" else "unselected").css
        addCss(statusClass)

        if ((selected && selectedIcon) || (! selected && unselectedIcon)) {
            div("switch-thumb-icon".css, statusClass) {
                icon(if (selected) browserIcons.switchSelected else browserIcons.switchUnselected, size = 16)
            }
        } else {
            div("switch-thumb".css, statusClass) {}
        }

        onClick { onChange(!selected) }
    }