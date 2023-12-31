/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.ir.rui.rum

import hu.simplexion.z2.kotlin.ir.rui.RUI_HIGHER_ORDER_CALL
import hu.simplexion.z2.kotlin.ir.rui.rum.visitors.RumElementVisitor
import org.jetbrains.kotlin.ir.expressions.IrCall

class RumParameterFunctionCall(
    rumClass: RumClass,
    index: Int,
    irCall: IrCall,
) : RumCall(rumClass, index, irCall) {

    override val name = "$RUI_HIGHER_ORDER_CALL$index"

    override val target
        get() = throw IllegalStateException("target of a parameter functions shouldn't be accessed")

    override fun <R, D> accept(visitor: RumElementVisitor<R, D>, data: D): R =
        visitor.visitParameterFunctionCall(this, data)

    override fun <D> acceptChildren(visitor: RumElementVisitor<Unit, D>, data: D) {
        valueArguments.forEach { it.accept(visitor, data) }
    }
}