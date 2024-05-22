/*
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.adaptive.ui.common.browser.adapter

import hu.simplexion.adaptive.foundation.AdaptiveAdapter
import hu.simplexion.adaptive.foundation.AdaptiveFragment
import hu.simplexion.adaptive.ui.common.browser.fragment.BrowserFragmentFactory
import hu.simplexion.adaptive.utility.vmNowMicro
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.w3c.dom.Node

open class AdaptiveBrowserAdapter(
    override val rootContainer: Node = requireNotNull(window.document.body) { "window.document.body is null or undefined" }
) : AdaptiveAdapter {

    override val fragmentFactory = BrowserFragmentFactory

    var nextId = 1L

    override val startedAt = vmNowMicro()

    override lateinit var rootFragment: AdaptiveFragment

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    override var trace = false

    override fun addActual(fragment: AdaptiveFragment, anchor : AdaptiveFragment?) {
        if (fragment is AdaptiveBrowserFragment) {
            rootContainer.appendChild(fragment.receiver)
        }
        // FIXME check(fragment is AdaptiveRootFragment)
    }

    override fun removeActual(fragment: AdaptiveFragment) {
        if (fragment is AdaptiveBrowserFragment) {
            rootContainer.removeChild(fragment.receiver)
        }
        // FIXME check(fragment is AdaptiveRootFragment)
    }

    override fun newId(): Long =
        nextId++

}