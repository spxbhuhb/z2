/*
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.adaptive.ui.common.uikit.adapter

import hu.simplexion.adaptive.foundation.Adaptive
import hu.simplexion.adaptive.foundation.AdaptiveAdapter
import hu.simplexion.adaptive.foundation.AdaptiveEntry
import hu.simplexion.adaptive.foundation.AdaptiveFragmentFactory
import platform.UIKit.UIView

/**
 * The entry point of an Adaptive iOS component tree.
 *
 * **IMPORTANT** variables declared outside the block are **NOT** reactive
 */
@AdaptiveEntry
fun ios(
    rootView : UIView,
    vararg imports : AdaptiveFragmentFactory,
    @Adaptive block: (adapter : AdaptiveAdapter) -> Unit
) : AdaptiveIOSAdapter =
    AdaptiveIOSAdapter(
        rootView
    ).also {
        it.fragmentFactory += imports
        block(it)
        it.mounted()
    }