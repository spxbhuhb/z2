package hu.simplexion.z2.browser.material.timepicker

import hu.simplexion.z2.browser.browserIcons
import hu.simplexion.z2.browser.field.FieldStyle
import hu.simplexion.z2.browser.material.textfield.FieldConfig.Companion.defaultFieldStyle
import hu.simplexion.z2.localization.icon.LocalizedIcon
import kotlinx.datetime.LocalTime

class TimePickerConfig(
    val style : FieldStyle = defaultFieldStyle
) {

    var leadingIcon: LocalizedIcon? = null
        set(value) {
            field = value
            update?.invoke()
        }

    var trailingIcon: LocalizedIcon? = browserIcons.schedule
        set(value) {
            field = value
            update?.invoke()
        }

    var errorIcon: LocalizedIcon = browserIcons.error
        set(value) {
            field = value
            update?.invoke()
        }

    var onChange: (DockedTimePicker.(value: LocalTime) -> Unit)? = null
        set(value) {
            field = value
            update?.invoke()
        }

    var onEnter: (() -> Unit)? = null
        set(value) {
            field = value
            update?.invoke()
        }

    var onEscape: (() -> Unit)? = null
        set(value) {
            field = value
            update?.invoke()
        }

    var update : (() -> Unit)? = null

}