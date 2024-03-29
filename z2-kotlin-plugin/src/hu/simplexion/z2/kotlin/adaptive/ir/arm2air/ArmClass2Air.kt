package hu.simplexion.z2.kotlin.adaptive.ir.arm2air

import hu.simplexion.z2.kotlin.adaptive.Names
import hu.simplexion.z2.kotlin.adaptive.ir.AdaptivePluginContext
import hu.simplexion.z2.kotlin.adaptive.ir.ClassBoundIrBuilder
import hu.simplexion.z2.kotlin.adaptive.ir.air.AirClass
import hu.simplexion.z2.kotlin.adaptive.ir.arm.ArmClass
import org.jetbrains.kotlin.backend.common.ir.addDispatchReceiver
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.ir.builders.declarations.*
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.declarations.impl.IrTypeParameterImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl
import org.jetbrains.kotlin.ir.types.makeNullable
import org.jetbrains.kotlin.ir.types.typeWith
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET
import org.jetbrains.kotlin.ir.util.constructors
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.SpecialNames
import org.jetbrains.kotlin.types.Variance

class ArmClass2Air(
    context: AdaptivePluginContext,
    val armClass: ArmClass
) : ClassBoundIrBuilder(context) {

    fun toAir(): AirClass {

        val originalFunction = armClass.originalFunction

        irClass = pluginContext.irContext.irFactory.buildClass {
            startOffset = originalFunction.startOffset
            endOffset = originalFunction.endOffset
            origin = IrDeclarationOrigin.DEFINED
            name = armClass.name
            kind = ClassKind.CLASS
            visibility = originalFunction.visibility
            modality = Modality.OPEN
        }

        typeParameters()

        irClass.parent = originalFunction.file
        irClass.superTypes = listOf(pluginContext.adaptiveGeneratedFragmentClass.typeWith(irClass.typeParameters.first().defaultType))
        irClass.metadata = armClass.originalFunction.metadata

        thisReceiver()
        val constructor = constructor()
        val patch = patch()

        val adapter = addPropertyWithConstructorParameter(Names.ADAPTIVE_ADAPTER_PROP, classBoundAdapterType, overridden = pluginContext.adaptiveAdapter)
        val closure = addPropertyWithConstructorParameter(Names.ADAPTIVE_CLOSURE_PROP, classBoundClosureType.makeNullable(), overridden = pluginContext.adaptiveClosure)
        val parent = addPropertyWithConstructorParameter(Names.ADAPTIVE_PARENT_PROP, classBoundFragmentType.makeNullable(), overridden = pluginContext.adaptiveParent)
        val externalPatch = addPropertyWithConstructorParameter(Names.ADAPTIVE_EXTERNAL_PATCH_PROP, classBoundExternalPatchType, overridden = pluginContext.adaptiveExternalPatch)

        val fragment = addIrProperty(Names.ADAPTIVE_CONTAINED_FRAGMENT_PROP, pluginContext.adaptiveFragmentType, inIsVar = false, overridden = pluginContext.adaptiveFragment)

        val initializer = initializer()

        create()
        mount()
        unmount()
        dispose()

        airClass = AirClass(
            armClass,
            armClass.parentScope?.fqName,
            irClass,
            adapter,
            closure,
            parent,
            externalPatch,
            fragment,
            constructor,
            initializer,
            patch
        )

        airClass.rendering = armClass.rendering.toAir(this)
        airClass.stateVariableList = armClass.stateVariables.map { it.toAir(this@ArmClass2Air) }
        airClass.stateVariableMap = airClass.stateVariableList.associateBy { it.armElement.originalName }
        airClass.dirtyMasks = armClass.dirtyMasks.map { it.toAir(this@ArmClass2Air) }

        return airClass
    }

    private fun typeParameters() {
        irClass.typeParameters = listOf(
            IrTypeParameterImpl(
                SYNTHETIC_OFFSET,
                SYNTHETIC_OFFSET,
                IrDeclarationOrigin.BRIDGE_SPECIAL,
                IrTypeParameterSymbolImpl(),
                Names.ADAPTIVE_BT,
                index = 0,
                isReified = false,
                variance = Variance.IN_VARIANCE,
                factory = irFactory
            ).also {
                it.parent = irClass
                it.superTypes = listOf(irBuiltIns.anyNType)
            }
        )
    }

    private fun thisReceiver(): IrValueParameter =

        irFactory.createValueParameter(
            SYNTHETIC_OFFSET,
            SYNTHETIC_OFFSET,
            IrDeclarationOrigin.INSTANCE_RECEIVER,
            IrValueParameterSymbolImpl(),
            SpecialNames.THIS,
            UNDEFINED_PARAMETER_INDEX,
            IrSimpleTypeImpl(irClass.symbol, false, emptyList(), emptyList()),
            varargElementType = null,
            isCrossinline = false,
            isNoinline = false,
            isHidden = false,
            isAssignable = false
        ).also {
            it.parent = irClass
            irClass.thisReceiver = it
        }

    private fun constructor(): IrConstructor =

        irClass.addConstructor {
            isPrimary = true
            returnType = irClass.typeWith()
        }.apply {
            parent = irClass

            body = irFactory.createBlockBody(SYNTHETIC_OFFSET, SYNTHETIC_OFFSET).apply {

                statements += IrDelegatingConstructorCallImpl.fromSymbolOwner(
                    SYNTHETIC_OFFSET,
                    SYNTHETIC_OFFSET,
                    irBuiltIns.anyType,
                    irBuiltIns.anyClass.constructors.first(),
                    typeArgumentsCount = 0,
                    valueArgumentsCount = 0
                )

                statements += IrInstanceInitializerCallImpl(
                    SYNTHETIC_OFFSET,
                    SYNTHETIC_OFFSET,
                    irClass.symbol,
                    irBuiltIns.unitType
                )
            }
        }

    private fun initializer(): IrAnonymousInitializer =

        irFactory.createAnonymousInitializer(
            SYNTHETIC_OFFSET, SYNTHETIC_OFFSET,
            origin = IrDeclarationOrigin.DEFINED,
            symbol = IrAnonymousInitializerSymbolImpl(),
            isStatic = false
        ).apply {
            parent = irClass
            // we should not add the initializer here as it should be the last
            // declaration of the class to be able to access all properties
            // it is added in finalize
        }

    private fun create(): IrSimpleFunction =

        irFactory.buildFun {
            name = Names.ADAPTIVE_CREATE_FUN
            returnType = irBuiltIns.unitType
            modality = Modality.OPEN
            isFakeOverride = true
        }.also { function ->

            function.overriddenSymbols = pluginContext.adaptiveCreate
            function.parent = irClass

            function.addDispatchReceiver {
                type = irClass.typeWith(irClass.typeParameters.first().defaultType)
            }

            irClass.declarations += function
        }

    @Suppress("DuplicatedCode")  // I don't want to merge mount and unmount
    private fun mount(): IrSimpleFunction =

        irFactory.buildFun {
            name = Names.ADAPTIVE_MOUNT_FUN
            returnType = irBuiltIns.unitType
            modality = Modality.OPEN
            isFakeOverride = true
        }.also { function ->

            function.overriddenSymbols = pluginContext.adaptiveMount
            function.parent = irClass

            function.addDispatchReceiver {
                type = irClass.typeWith(irClass.typeParameters.first().defaultType)
            }

            function.addValueParameter {
                name = Name.identifier("bridge")
                type = pluginContext.adaptiveBridgeType
            }

            irClass.declarations += function
        }

    private fun patch(): IrSimpleFunction =

        irFactory.buildFun {
            name = Names.ADAPTIVE_PATCH_FUN
            returnType = irBuiltIns.unitType
            modality = Modality.OPEN
        }.also { function ->

            function.overriddenSymbols = pluginContext.adaptivePatch
            function.parent = irClass

            function.addDispatchReceiver {
                type = irClass.typeWith(irClass.typeParameters.first().defaultType)
            }

            irClass.declarations += function

        }

    @Suppress("DuplicatedCode")  // I don't want to merge mount and unmount
    private fun unmount(): IrSimpleFunction =

        irFactory.buildFun {
            name = Names.ADAPTIVE_UNMOUNT_FUN
            returnType = irBuiltIns.unitType
            modality = Modality.OPEN
            isFakeOverride = true
        }.also { function ->

            function.overriddenSymbols = pluginContext.adaptiveUnmount
            function.parent = irClass

            function.addDispatchReceiver {
                type = irClass.typeWith(irClass.typeParameters.first().defaultType)
            }

            function.addValueParameter {
                name = Name.identifier("bridge")
                type = pluginContext.adaptiveBridgeType
            }

            irClass.declarations += function
        }

    private fun dispose(): IrSimpleFunction =

        irFactory.buildFun {
            name = Names.ADAPTIVE_DISPOSE_FUN
            returnType = irBuiltIns.unitType
            modality = Modality.OPEN
            isFakeOverride = true
        }.also { function ->

            function.overriddenSymbols = pluginContext.adaptiveDispose
            function.parent = irClass

            function.addDispatchReceiver {
                type = irClass.typeWith(irClass.typeParameters.first().defaultType)
            }

            irClass.declarations += function
        }

}