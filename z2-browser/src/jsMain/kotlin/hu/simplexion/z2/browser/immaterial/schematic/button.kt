package hu.simplexion.z2.browser.immaterial.schematic

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.localization.text.LocalizedText
import hu.simplexion.z2.schematic.Schematic
import org.w3c.dom.events.Event

/**
 * A text button that is disabled when [schematic] is invalid.
 */
fun Z2.schematicTextButton(schematic: Schematic<*>, title : LocalizedText, onClick : (event : Event) -> Unit) =
    textButton(title, onClick).also { button ->
        button.isDisabled = schematic.isValid
    }