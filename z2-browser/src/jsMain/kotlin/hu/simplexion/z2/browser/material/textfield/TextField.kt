package hu.simplexion.z2.browser.material.textfield

import hu.simplexion.z2.browser.field.FieldState
import hu.simplexion.z2.browser.html.Z2

class TextField(
    parent: Z2,
    state: FieldState = FieldState(),
    config: FieldConfig<String>
) : AbstractField<String>(
    parent, state, config
) {

    override fun main(): TextField {
        super.main()
        return this
    }
}