/*
 * Copyright © 2022-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin

import hu.simplexion.z2.kotlin.ir.Z2GenerationExtension
import hu.simplexion.z2.kotlin.ir.Z2Options
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

/**
 * Registers the extensions into the compiler.
 */
@OptIn(ExperimentalCompilerApi::class)
class Z2CompilerPluginRegistrar : CompilerPluginRegistrar() {

    override val supportsK2 = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val options = Z2Options(
            resourceOutputDir = configuration.get(Z2Options.CONFIG_KEY_RESOURCE_DIR)!!
        )

        IrGenerationExtension.registerExtension(Z2GenerationExtension(options))
        FirExtensionRegistrarAdapter.registerExtension(Z2PluginRegistrar())
    }

}
