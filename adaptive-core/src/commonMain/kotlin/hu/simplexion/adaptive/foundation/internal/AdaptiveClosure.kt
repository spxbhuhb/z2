/*
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.adaptive.foundation.internal

import hu.simplexion.adaptive.foundation.AdaptiveFragment
import hu.simplexion.adaptive.foundation.ops

/**
 * @property  closureSize  The total number of state variables in this closure. This is the sum of the number
 *                         of state variables in [owner] and all [components].
 */
class AdaptiveClosure(
    val components: Array<AdaptiveFragment>,
    val closureSize: Int
) {
    val owner
        get() = components[0]

    var declarationScopeSize = owner.state.size

    /**
     * Get a state variable by its index in the closure. Walks over the scopes in the
     * closure to find the state and then fetches the variable form that state.
     */
    fun get(stateVariableIndex: Int): Any? {
        if (stateVariableIndex < declarationScopeSize) {
            return owner.state[stateVariableIndex]
        }

        // indices : 0 1 / 2 / 3 4 5 6 (declaration, anonymous 1, ANONYMOUS-2)
        // requested index: 4
        // closure size of ANONYMOUS-2: 7
        // state size of ANONYMOUS-2: 4
        // index of the first variable of ANONYMOUS-2 in the closure: 3 = closure size - state size
        // index of the requested variable of ANONYMOUS-2 in the state of ANONYMOUS-2: requested index - ANONYMOUS-2 index

        for (anonymousScope in components) {
            val extendedClosureSize = anonymousScope.thisClosure.closureSize
            if (extendedClosureSize > stateVariableIndex) {
                return anonymousScope.state[stateVariableIndex - (extendedClosureSize - anonymousScope.state.size)]
            }
        }

        invalidIndex("get", stateVariableIndex)
    }

    /**
     * Set a state variable by its index in the closure. Walks over the scopes in the
     * closure to find the state and then sets the variable of that state.
     */
    fun set(stateVariableIndex: Int, value: Any?) {
        if (stateVariableIndex < declarationScopeSize) {
            owner.setStateVariable(stateVariableIndex, value)
            return
        }

        for (anonymousScope in components) {
            val extendedClosureSize = anonymousScope.thisClosure.closureSize
            if (extendedClosureSize > stateVariableIndex) {
                anonymousScope.setStateVariable(stateVariableIndex - (extendedClosureSize - anonymousScope.state.size), value)
                return
            }
        }

        invalidIndex("set", stateVariableIndex)
    }

    /**
     * Calculate the complete closure mask (or of components masks).
     */
    fun closureDirtyMask(): StateVariableMask {
        var mask = 0
        var position = 0
        for (component in components) {
            if (component.dirtyMask == initStateMask) {
                mask = initStateMask
            } else {
                mask = mask or (component.dirtyMask shl position)
            }
            position += component.state.size
        }
        return mask
    }

    override fun toString(): String {
        return "$owner"
    }

    fun dump(): String {
        val builder = StringBuilder()
        builder.append("AdaptiveClosure:\n")
        builder.append("Owner: $owner\n")
        builder.append("Components:\n")
        for (component in components) {
            builder.append("\t$component  ${component.state.contentToString()}\n")
        }
        return builder.toString()
    }

    private fun invalidIndex(point: String, index : Int) : Nothing {
        ops(
            "invalidStateVariableIndex",
            """
                the code tried to reach a data that is simply not there,
                this might be an error in Adaptive or in your code,
                if this is a manually implemented fragment, check it for bugs,
                otherwise, please open a GitHub issue or contact me on Slack,
                point: $point index: $index closure: ${this.dump()}
            """
        )
    }
}