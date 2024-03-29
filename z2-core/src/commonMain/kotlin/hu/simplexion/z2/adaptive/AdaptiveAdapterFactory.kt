/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.adaptive

abstract class AdaptiveAdapterFactory {
    abstract fun accept(vararg args: Any?): AdaptiveAdapter<*>?
}
