/*
 * Copyright © 2022-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.schematic.ir.companion

import hu.simplexion.z2.kotlin.schematic.SCHEMATIC_COMPANION_GET_FIELD_VALUE
import hu.simplexion.z2.kotlin.schematic.ir.SchematicPluginContext
import hu.simplexion.z2.kotlin.schematic.ir.klass.SchematicFieldVisitor
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.builders.declarations.addValueParameter
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrBranch
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.defaultType

class GetFieldValue(
    pluginContext: SchematicPluginContext,
    val companionTransform: CompanionTransform,
) : AbstractCompanionFun(
    pluginContext,
    companionTransform,
    SCHEMATIC_COMPANION_GET_FIELD_VALUE,
    pluginContext.schematicCompanionGetFieldValue
) {

    companion object {
        const val ARG_INSTANCE = 0
        const val ARG_FIELD_NAME = 1
    }

    override val returnType: IrType
        get() = irBuiltIns.anyNType


    override fun IrSimpleFunction.addParameters() {
        addValueParameter("instance", transformedClass.defaultType)
        addValueParameter("fieldName", irBuiltIns.stringType)
    }

    override fun IrSimpleFunction.buildBody() {
        body = DeclarationIrBuilder(irContext, this.symbol).irBlockBody {
            + irReturn(
                irWhen(
                    returnType,
                    companionTransform.classTransform.fieldVisitors.map { toBranch(it, this@buildBody) }
                ).also {
                    it.branches += irElseBranch(throwSchemaFieldNotFound(transformedClass, irGet(this@buildBody.valueParameters[ARG_FIELD_NAME])))
                }
            )
        }
    }

    fun IrBlockBodyBuilder.toBranch(visitor: SchematicFieldVisitor, function: IrSimpleFunction): IrBranch {
        val property = visitor.property
        return irBranch(
            irEqual(irConst(property.name.identifier), irGet(function.valueParameters[ARG_FIELD_NAME])),
            irCall(
                property.getter !!.symbol,
                dispatchReceiver = irGet(function.valueParameters[ARG_INSTANCE]),
            )
        )
    }

}
