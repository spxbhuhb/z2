package hu.simplexion.z2.browser.material.menu

import hu.simplexion.z2.browser.css.*
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.material.StateLayer
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.localization.icon.LocalizedIcon
import hu.simplexion.z2.localization.locales.localeCapitalized
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class MenuItemBase<T>(
    parent: Z2? = null,
    val value: T,
    icon: LocalizedIcon? = null,
    leading: Z2Builder? = null,
    label: String,
    trailing: Z2Builder? = null,
    disabled: Boolean,
    onSelected: (item: MenuItemBase<T>) -> Unit
) : Z2(
    parent,
    document.createElement("div") as HTMLElement,
    classes = arrayOf(displayGrid, pl12, pr12, cursorPointer, alignSelfCenter, onSurfaceText, positionRelative)
) {

    init {
        gridTemplateColumns = "min-content 1fr min-content"
        gridTemplateRows = "minmax(48px, 1fr)"

        StateLayer(this, arrayOf(wFull, heightFull), disabled)

        div(alignSelfCenter, pr12) {
            if (icon != null) icon(icon, size = 24)
            if (leading != null) div(pl12) { leading() }
        }

        div(labelLarge, justifySelfStart, whiteSpaceNoWrap, alignSelfCenter) {
            text { label.localeCapitalized }
        }

        div(alignSelfCenter) {
            if (trailing != null) trailing()
        }

        if (! disabled) {
            onClick { onSelected(this@MenuItemBase) }
        }
    }

}