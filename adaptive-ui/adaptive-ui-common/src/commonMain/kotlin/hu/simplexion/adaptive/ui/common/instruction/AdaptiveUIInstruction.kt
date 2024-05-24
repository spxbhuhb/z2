/*
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package hu.simplexion.adaptive.ui.common.instruction

import hu.simplexion.adaptive.foundation.instruction.AdaptiveInstruction

interface AdaptiveUIInstruction : AdaptiveInstruction {
    fun apply(uiInstructions: UIInstructions)
}