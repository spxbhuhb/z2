/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.adaptive

/**
 * Interface to implement by fragments generated by the compiler plugin.
 * This interface may define functions the generated fragments use, so
 * we do not have to code them in IR.
 */
@AdaptivePublicApi
interface AdaptiveGeneratedFragment<BT> : AdaptiveFragment<BT> {

    /**
     * The top level fragment of this scope. This may be:
     *
     * - a structural fragment such as block, when or a loop
     * - direct call to another adaptive function
     *
     * Set to [AdaptiveFragment.adaptiveClosure] by the initializer of the class
     * that implements [AdaptiveGeneratedFragment].
     */
    val containedFragment: AdaptiveFragment<BT>

    override fun adaptiveCreate() {
        if (adaptiveAdapter.trace) adaptiveAdapter.trace(this::class.simpleName ?: "<generated>", "create")
        containedFragment.adaptiveCreate()
    }

    override fun adaptiveMount(bridge: AdaptiveBridge<BT>) {
        if (adaptiveAdapter.trace) adaptiveAdapter.trace(this::class.simpleName ?: "<generated>", "mount", "bridge", bridge)
        containedFragment.adaptiveMount(bridge)
    }

    // do not override adaptivePatch, it should be generated in all cases

    override fun adaptiveUnmount(bridge: AdaptiveBridge<BT>) {
        if (adaptiveAdapter.trace) adaptiveAdapter.trace(this::class.simpleName ?: "<generated>", "unmount", "bridge", bridge)
        containedFragment.adaptiveUnmount(bridge)
    }

    override fun adaptiveDispose() {
        if (adaptiveAdapter.trace) adaptiveAdapter.trace(this::class.simpleName ?: "<generated>", "dispose")
        containedFragment.adaptiveDispose()
    }

}