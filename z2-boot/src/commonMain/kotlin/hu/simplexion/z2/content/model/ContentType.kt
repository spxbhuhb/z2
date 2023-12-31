package hu.simplexion.z2.content.model

import hu.simplexion.z2.schematic.Schematic

class ContentType : Schematic<ContentType>() {
    var uuid by uuid<ContentType>(true)

    var extension by string() blank false minLength 1 maxLength 20
    var mimeType by string() maxLength 100
    var sizeLimit by long(5L*1024L*1024L) min 0
    var allowed by boolean(true)
}