/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "TestFunctionName", "unused")

package hu.simplexion.z2.adaptive.test.original

import hu.simplexion.z2.adaptive.Adaptive

@Adaptive
fun Use() {
    Comp() value "a"
}

@Adaptive
fun Comp(value: String = ""): TransformComp {
    // Text(value)
    return TransformComp
}

object TransformComp

infix fun TransformComp.value(v: String) = Unit


@Adaptive
fun Wrapper(@Adaptive block: TransformBlock.() -> Unit) {
    var name = "block"
    //Text("before the $block")
    TransformBlock.block()
    //Text("after the $block")
}

object TransformBlock {
    var name: String = ""
}

@Adaptive
fun Counter() {
    var count = 0
    Wrapper {
        name = "Wrapped Block"
        // Button { "click count: $count" } onClick { count ++ }
    }
}

