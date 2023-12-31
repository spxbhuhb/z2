/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.rui.test.sandbox

import hu.simplexion.z2.rui.Rui
import hu.simplexion.z2.rui.RuiAdapterRegistry
import hu.simplexion.z2.rui.rui
import hu.simplexion.z2.rui.testing.RuiTestAdapter
import hu.simplexion.z2.rui.testing.RuiTestAdapter.TraceEvent
import hu.simplexion.z2.rui.testing.RuiTestAdapterFactory
import hu.simplexion.z2.rui.testing.T1

@Rui
fun Basic(i: Int) {
    val i2 = 12
    T1(0)
//    T1(i)
//    T1(i2)
//    T1(i + i2)
//    if (i == 1) {
//        T1(i2)
//    }
//    when {
//        i == 1 -> T1(i2 + 1)
//        i == 2 -> T1(i2 + 2)
//    }
//    for (fi in i..i2) {
//        T1(fi + i2)
//    }
}

fun box() : String {

    RuiAdapterRegistry.register(RuiTestAdapterFactory)

    rui {
        Basic(11)
    }

    return RuiTestAdapter.assert(listOf(
        TraceEvent("RuiT1", "init"),
        TraceEvent("RuiT1", "create", "p0:", "1"),
        TraceEvent("RuiT1", "mount", "bridge:", "1")
    ))
}