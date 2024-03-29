package hu.simplexion.z2.kotlin.services.ir.util

import hu.simplexion.z2.kotlin.services.FUN_NAMES_TO_SKIP
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.types.getClass

class ServiceFunctionCache {

    val services = mutableMapOf<IrType, List<IrSimpleFunctionSymbol>>()

    operator fun get(serviceType: IrType) =
        services.getOrPut(serviceType) { add(serviceType) }

    fun add(serviceType: IrType): List<IrSimpleFunctionSymbol> {
        val result = mutableListOf<IrSimpleFunctionSymbol>()

        val serviceClass = checkNotNull(serviceType.getClass()) { "missing service interface ${serviceType.classFqName}" }
        for (declaration in serviceClass.declarations) {
            if (declaration !is IrSimpleFunction) continue
            if (declaration.name.identifier in FUN_NAMES_TO_SKIP) continue
            require(declaration.isSuspend) { "Service function ${declaration.symbol} should be suspend!" }
            result += declaration.symbol
        }

        return result
    }

}