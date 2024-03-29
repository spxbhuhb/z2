/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("FunctionName")

package hu.simplexion.z2.adaptive.dom.html

import hu.simplexion.z2.adaptive.*
import org.w3c.dom.Node

@Adaptive
@AdaptivePublicApi
fun Text(content: String) {
}

@AdaptivePublicApi
class AdaptiveText(
    adaptiveAdapter: AdaptiveAdapter<Node>,
    override val adaptiveClosure: AdaptiveClosure<Node>?,
    override val adaptiveParent: AdaptiveFragment<Node>?,
    adaptiveExternalPatch: AdaptiveExternalPatchType<Node>,
    var content: String,
) : LeafNode(adaptiveAdapter, adaptiveExternalPatch) {

    override val receiver = org.w3c.dom.Text()

    var adaptiveDirty0 = 0L

    @AdaptivePublicApi
    fun adaptiveInvalidate0(mask: Long) {
        adaptiveDirty0 = adaptiveDirty0 or mask
    }

    override fun adaptiveCreate() {
        receiver.data = content
    }

    override fun adaptivePatch() {
        if (adaptiveDirty0 and 1L != 0L) {
            receiver.data = content
        }
    }

}