/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.rui

class RuiPlaceholder<BT>(
    override val ruiAdapter: RuiAdapter<BT>
) : RuiFragment<BT> {

    override val ruiScope = null

    override val ruiExternalPatch: RuiExternalPathType<BT> = { _, scopeMask -> scopeMask }

    val bridge = ruiAdapter.createPlaceholder()

    override fun ruiCreate() {

    }

    override fun ruiMount(bridge: RuiBridge<BT>) {
        bridge.add(this.bridge)
    }

    override fun ruiPatch(scopeMask: Long) {

    }

    override fun ruiUnmount(bridge: RuiBridge<BT>) {
        bridge.remove(this.bridge)
    }

    override fun ruiDispose() {

    }

}