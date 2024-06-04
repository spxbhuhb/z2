/*
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package hu.simplexion.adaptive.ui.common.instruction

import hu.simplexion.adaptive.foundation.instruction.AdaptiveInstruction
import hu.simplexion.adaptive.foundation.internal.cleanStateMask
import hu.simplexion.adaptive.ui.common.AdaptiveUIFragment
import hu.simplexion.adaptive.ui.common.RenderData
import hu.simplexion.adaptive.utility.alsoIfInstance

class AdaptiveUIEvent(
    val fragment: AdaptiveUIFragment<*>,
    val nativeEvent : Any?
) {
    fun patchIfDirty() {
        val closureOwner = fragment.createClosure.owner
        if (closureOwner.dirtyMask != cleanStateMask) {
            closureOwner.patchInternal()
        }
    }
}

fun onClick(handler : (event : AdaptiveUIEvent) -> Unit) = OnClick(handler)

class OnClick(
    private val handler : (event : AdaptiveUIEvent) -> Unit
) : AdaptiveInstruction {

    override fun apply(subject: Any) {
        subject.alsoIfInstance<RenderData> { it.onClick = this }
    }

    fun execute(event : AdaptiveUIEvent) {
        handler(event)
        event.patchIfDirty()
    }

    override fun toString(): String {
        return "OnClick"
    }
}