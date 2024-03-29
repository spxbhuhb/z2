package hu.simplexion.z2.browser.util

import hu.simplexion.z2.browser.css.heightFull
import hu.simplexion.z2.browser.css.opacity38
import hu.simplexion.z2.browser.css.surface
import hu.simplexion.z2.browser.css.wFull
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.util.localLaunch
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import org.w3c.dom.get

var uniqueNodeId = 0
    get() = field++

fun launchBlocking(func: suspend () -> Unit) {
    val overlay = Z2().also {
        it.addCss(wFull, heightFull, surface, opacity38)
        it.zIndex = 100
        it.style.position = "fixed"
        it.style.top = "0"
        document.body!!.append(it.htmlElement)
    }

    localLaunch {
        func()
        overlay.htmlElement.remove()
    }
}

fun <T> T.launchApply(func: suspend T.() -> Unit) =
    localLaunch {
        try {
            this@launchApply.func()
        } catch (ex: Throwable) {
            // TODO add a function to Application to channel all errors into one place, notify the user, upload the error report, etc.
            ex.printStackTrace()
        }
    }

fun io(func: suspend () -> Unit) =
    localLaunch {
        try {
            func()
        } catch (ex: Throwable) {
            // TODO add a function to Application to channel all errors into one place, notify the user, upload the error report, etc.
            ex.printStackTrace()
        }
    }

/**
 * Look for an HTML element which has a DOM Node Id with the given prefix and is
 * an ancestor of the event target.
 *
 * @param  event  the browser event to get target from
 * @param  idPrefix  the id prefix to look for
 * @param  removePrefix  when true only part of the DOM Node Id after the prefix is returned
 *
 * @return  null if there is no element with the proper id between the ancestors
 *          a pair of the element and the id of the element (or part of id after the prefix if removePrefix is true)
 */
fun getElementId(event: Event, idPrefix: String, removePrefix: Boolean = true): Pair<HTMLElement, String>? {
    var current = event.target as Node?

    while (current != null) {
        if (current is HTMLElement && current.id.startsWith(idPrefix)) break
        current = current.parentNode
    }

    if (current == null) {
        return null
    }

    current as HTMLElement

    return if (removePrefix) {
        current to current.id.substring(idPrefix.length)
    } else {
        current to current.id
    }
}

/**
 * Get an HTML dataset entry from the element or from one of its
 * ancestors. Traverses up in the DOM until reaches root or finds
 * an element with a dataset that holds the given key.
 *
 * @return null when no data has been found, value of the data set
 *         entry otherwise
 */
fun HTMLElement.getDatasetEntry(key: String): String? {
    val value = this.dataset[key]
    if (value != null) return value

    return (this.parentElement as? HTMLElement)?.getDatasetEntry(key)
}