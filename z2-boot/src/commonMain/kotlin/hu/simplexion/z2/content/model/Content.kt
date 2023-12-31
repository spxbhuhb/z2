package hu.simplexion.z2.content.model

import hu.simplexion.z2.schematic.Schematic

class Content : Schematic<Content>() {
    var uuid by uuid<Content>()
    var name by string() blank false maxLength 1000 pattern Regex("[\\p{Alpha}\\d ._!()\\-,%]{1,1000}")
    var type by string() maxLength 40
    var size by long() min 0
    var sha256 by string().nullable() maxLength 44
    var status by enum<ContentStatus>()
}