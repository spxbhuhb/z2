/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.adaptive.dom

import hu.simplexion.z2.adaptive.AdaptiveAdapter
import hu.simplexion.z2.adaptive.AdaptiveAdapterRegistry
import hu.simplexion.z2.adaptive.AdaptiveBridge
import kotlinx.browser.window
import org.w3c.dom.Node

/**
 * The default adapter for W3C DOM nodes used in browsers.
 */
open class AdaptiveDOMAdapter(
    val node: Node = requireNotNull(window.document.body) { "window.document.body is null or undefined" }
) : AdaptiveAdapter<Node> {


    override val rootBridge = AdaptiveDOMPlaceholder().also {
        node.appendChild(it.receiver)
    }

    override var trace = false

    override fun createPlaceholder(): AdaptiveBridge<Node> {
        return AdaptiveDOMPlaceholder()
    }

    override fun newId(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        init {
            AdaptiveAdapterRegistry.register(AdaptiveDOMAdapterFactory)
        }
    }
}