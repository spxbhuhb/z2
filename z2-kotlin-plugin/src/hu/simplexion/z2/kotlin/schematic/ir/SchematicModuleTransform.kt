/*
 * Copyright © 2022-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.schematic.ir

import hu.simplexion.z2.kotlin.schematic.ir.klass.SchematicClassTransform
import hu.simplexion.z2.kotlin.schematic.ir.store.SchematicEntityStoreTransform
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.util.isSubclassOf

class SchematicModuleTransform(
    private val pluginContext: SchematicPluginContext
) : IrElementTransformerVoidWithContext() {

    val classTransforms = mutableListOf<SchematicClassTransform>()

    override fun visitClassNew(declaration: IrClass): IrStatement {

        val schematic = declaration.isSubclassOf(pluginContext.schematicClass.owner)
        val schematicEntity = declaration.isSubclassOf(pluginContext.schematicEntityClass.owner)

        if (schematic || schematicEntity) {
            SchematicClassTransform(pluginContext).also {
                it.initialize(declaration)
                classTransforms += it
            }

            return declaration
        }

        val schematicEntityStore = declaration.isSubclassOf(pluginContext.schematicEntityStoreClass.owner)

        if (schematicEntityStore) {
            declaration.accept(SchematicEntityStoreTransform(pluginContext), null)
            return declaration
        }

        return declaration
    }

    fun transformFields() {
        classTransforms.forEach { it.transformFields() }
    }

}
