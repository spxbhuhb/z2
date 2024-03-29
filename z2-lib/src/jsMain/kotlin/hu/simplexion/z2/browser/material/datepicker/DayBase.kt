package hu.simplexion.z2.browser.material.datepicker

import hu.simplexion.z2.browser.css.*
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.util.hereAndNow
import kotlinx.browser.document
import kotlinx.datetime.LocalDate
import org.w3c.dom.HTMLElement

class DayBase(
    parent: Z2? = null,
    val date: LocalDate = hereAndNow().date,
    otherMonth: Boolean,
    dense: Boolean,
    marked: Boolean,
    today: Boolean,
    onSelected: (date: LocalDate) -> Unit
) : Z2(
    parent,
    document.createElement("div") as HTMLElement,
    classes = arrayOf(boxSizingBorderBox, displayFlex, justifyContentCenter, cursorPointer, bodySmall, positionRelative, overflowHidden)
) {
    // TODO think about date picker class list, it is long and when rendering a whole year it is an unnecessary repetition
    val stateLayer = div(displayNone, primary, stateLayerOpacityHover, positionAbsolute, wFull, heightFull) {  }

    init {
        if (dense) addCss(h32, w32, borderRadius16) else addCss(h40, w40, borderRadius20)

        if (marked) {
            addCss(primary, onPrimaryText)
        } else {
            addCss(onSurfaceText)
            if (otherMonth) addCss(opacity38)
            if (today) addCss(borderPrimary)
        }

        div(alignSelfCenter) { text { date.dayOfMonth } }

        onClick { onSelected(date) }

        onMouseEnter {
            stateLayer.removeCss(displayNone)
        }
        onMouseLeave {
            stateLayer.addCss(displayNone)
        }
    }

}