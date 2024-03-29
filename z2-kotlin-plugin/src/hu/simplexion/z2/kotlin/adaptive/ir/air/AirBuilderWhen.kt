package hu.simplexion.z2.kotlin.adaptive.ir.air

import hu.simplexion.z2.kotlin.adaptive.ir.ClassBoundIrBuilder
import hu.simplexion.z2.kotlin.adaptive.ir.air.visitors.AirElementVisitor
import hu.simplexion.z2.kotlin.adaptive.ir.air2ir.AirBuilderWhen2Ir
import hu.simplexion.z2.kotlin.adaptive.ir.arm.ArmWhen
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction

class AirBuilderWhen(
    override val armElement: ArmWhen,
    override val irFunction: IrSimpleFunction,
    override val externalPatch: AirFunction,
    override val subBuilders: List<AirBuilder>,
    val fragmentFactory: AirFragmentFactory
) : AirBuilder {

    override fun toIr(parent: ClassBoundIrBuilder) = AirBuilderWhen2Ir(parent, this).toIr()

    override fun <R, D> accept(visitor: AirElementVisitor<R, D>, data: D): R =
        visitor.visitBuilderWhen(this, data)

}