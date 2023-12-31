/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.ir.rui.rum

import hu.simplexion.z2.kotlin.ir.rui.RUI_BRANCH
import hu.simplexion.z2.kotlin.ir.rui.rum.visitors.RumElementVisitor
import org.jetbrains.kotlin.ir.expressions.IrBranch

class RumBranch(
    val rumClass: RumClass,
    val index: Int,
    val irBranch: IrBranch,
    val condition: RumExpression,
    val result: RumRenderingStatement
) : RumElement {

    val name = "$RUI_BRANCH$index"

    override fun <R, D> accept(visitor: RumElementVisitor<R, D>, data: D): R =
        visitor.visitBranch(this, data)

    override fun <D> acceptChildren(visitor: RumElementVisitor<Unit, D>, data: D) {
        condition.accept(visitor, data)
        result.accept(visitor, data)
    }
}