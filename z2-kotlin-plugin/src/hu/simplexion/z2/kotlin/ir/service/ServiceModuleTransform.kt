/*
 * Copyright © 2022-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.ir.service

import hu.simplexion.z2.kotlin.ir.service.consumer.ConsumerClassBuilder
import hu.simplexion.z2.kotlin.ir.service.impl.ImplClassTransform
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.util.hasAnnotation

class ServiceModuleTransform(
    private val pluginContext: ServicePluginContext
) : IrElementTransformerVoidWithContext() {

    override fun visitClassNew(declaration: IrClass): IrStatement {

        if (declaration.hasAnnotation(pluginContext.noTransformAnnotation)) {
            return declaration
        }

        if (declaration.superTypes.contains(pluginContext.serviceType)) {
            ConsumerClassBuilder(pluginContext, declaration).build()
            return declaration
        }

        if (declaration.superTypes.map { it.classFqName }.contains(pluginContext.serviceImplType.classFqName)) {
            return declaration.accept(ImplClassTransform(pluginContext), null) as IrStatement
        }

        return declaration
    }

}
