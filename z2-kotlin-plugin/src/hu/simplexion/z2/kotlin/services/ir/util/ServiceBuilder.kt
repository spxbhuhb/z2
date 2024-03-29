package hu.simplexion.z2.kotlin.services.ir.util

import hu.simplexion.z2.kotlin.services.SERVICE_IMPL_FQ_NAME
import hu.simplexion.z2.kotlin.services.ir.ServicesPluginContext
import hu.simplexion.z2.kotlin.util.AbstractIrBuilder
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.types.isSubtypeOfClass

interface ServiceBuilder : AbstractIrBuilder {

    override val pluginContext: ServicesPluginContext

    var serviceNameGetter: IrSimpleFunctionSymbol

    val overriddenServiceFunctions: MutableList<IrSimpleFunctionSymbol>

    val serviceNames : MutableList<String>

    fun collectServiceFunctions(klass : IrClass) {
        for (superType in klass.superTypes) {
            if (superType.isSubtypeOfClass(pluginContext.serviceClass) && superType.classFqName != SERVICE_IMPL_FQ_NAME) {
                serviceNames += superType.classFqName!!.asString()
                overriddenServiceFunctions += pluginContext.serviceFunctionCache[superType]
            }
        }
    }

    fun IrFunction.asServiceFun(): IrSimpleFunction? {
        if (this !is IrSimpleFunction) return null
        for (overriddenSymbol in this.overriddenSymbols) {
            if (overriddenSymbol in overriddenServiceFunctions) return this
        }
        return null
    }

    fun getServiceName(function: IrSimpleFunction): IrCallImpl =
        irCall(
            serviceNameGetter,
            IrStatementOrigin.GET_PROPERTY,
            dispatchReceiver = irGet(checkNotNull(function.dispatchReceiverParameter))
        )


}