/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.rui.testing

import hu.simplexion.z2.rui.RuiBridge

class RuiTestBridge(
    val id: Int
) : RuiBridge<TestNode> {

    override val receiver = TestNode()

    override fun remove(child: RuiBridge<TestNode>) {
        receiver.removeChild(child.receiver)
    }

    override fun replace(oldChild: RuiBridge<TestNode>, newChild: RuiBridge<TestNode>) {
        receiver.replaceChild(oldChild.receiver, newChild.receiver)
    }

    override fun add(child: RuiBridge<TestNode>) {
        receiver.appendChild(child.receiver)
    }

}