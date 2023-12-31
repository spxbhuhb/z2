/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.rui

/**
 * Entry point of a Rui component tree.
 *
 * **IMPORTANT** variables declared outside the block are **NOT** reactive
 */
fun rui(block: (ruiAdapter: RuiAdapter<*>) -> Unit) {
    block(RuiAdapterRegistry.adapterFor())
}

/**
 * Entry point of a Rui component tree with a specific adapter. The adapter
 * registry is not accessed in this case but the components will use the
 * adapter passed.
 *
 * **IMPORTANT** variables declared outside the block are **NOT** reactive
 */
fun <BT> rui(ruiAdapter: RuiAdapter<BT>, block: (ruiAdapter: RuiAdapter<BT>) -> Unit) {
    block(ruiAdapter)
}