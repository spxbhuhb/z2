/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.adaptive.success

import hu.simplexion.z2.adaptive.Adaptive
import hu.simplexion.z2.adaptive.adaptive
import hu.simplexion.z2.adaptive.AdaptiveAdapterRegistry
import hu.simplexion.z2.adaptive.testing.AdaptiveTestAdapter
import hu.simplexion.z2.adaptive.testing.AdaptiveTestAdapterFactory
import hu.simplexion.z2.adaptive.testing.AdaptiveTestAdapter.TraceEvent
import hu.simplexion.z2.adaptive.testing.T0
import hu.simplexion.z2.adaptive.testing.T1
import hu.simplexion.z2.adaptive.testing.RunOnMount

@Adaptive
fun IfElsePatch(i : Int) {
    if (i % 2 == 0) {
        T0()
    } else {
        T1(i)
    }
}

fun box() : String {

    AdaptiveAdapterRegistry.register(AdaptiveTestAdapterFactory)

    adaptive {
        var v1 = 5
        IfElsePatch(v1)
        RunOnMount { v1 = 6 }
    }

    return AdaptiveTestAdapter.assert(listOf(
        TraceEvent("<root>", "init", ),
        TraceEvent("AdaptiveSequence", "init", ),
        TraceEvent("AdaptiveIfElsePatch", "init", ),
        TraceEvent("AdaptiveWhen", "init", "newBranch:", "1"),
        TraceEvent("AdaptiveT1", "init", ),
        TraceEvent("AdaptiveRunOnMount", "init", ),

        TraceEvent("<root>", "create", ),
        TraceEvent("AdaptiveSequence", "create", ),
        TraceEvent("AdaptiveIfElsePatch", "create", ),
        TraceEvent("AdaptiveWhen", "create", ),
        TraceEvent("AdaptiveT1", "create", "p0:", "5"),
        TraceEvent("AdaptiveRunOnMount", "create", ),

        TraceEvent("<root>", "mount", "bridge", "1"),
        TraceEvent("AdaptiveSequence", "mount", "bridge:", "1"),
        TraceEvent("AdaptiveIfElsePatch", "mount", "bridge", "1"),
        TraceEvent("AdaptiveWhen", "mount", "bridge:", "1"),
        TraceEvent("AdaptiveT1", "mount", "bridge:", "2"),
        TraceEvent("AdaptiveRunOnMount", "mount", "bridge:", "1"),

        TraceEvent("<root>", "patch", ),
        TraceEvent("AdaptiveSequence", "patch", ),
        TraceEvent("AdaptiveIfElsePatch", "patch", ),
        TraceEvent("AdaptiveWhen", "patch", "branch:", "1", "newBranch:", "1"),
        TraceEvent("AdaptiveT1", "patch", "adaptiveDirty0:", "(size=1, value=0)", "p0:", "5"),
        TraceEvent("AdaptiveRunOnMount", "patch", )
    ))
}