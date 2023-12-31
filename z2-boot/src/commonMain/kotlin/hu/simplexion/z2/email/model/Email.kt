package hu.simplexion.z2.email.model

import hu.simplexion.z2.auth.model.Principal
import hu.simplexion.z2.schematic.Schematic

class Email : Schematic<Email>() {
    var uuid by uuid<Email>()

    var createdAt by instant()
    var createdBy by uuid<Principal>().nullable()

    var status by enum<EmailStatus>()
    var sentAt by instant().nullable()

    var sensitive by boolean()
    var hasAttachment by boolean()

    var recipients by string()
    var subject by string() maxLength 200

    var contentType by string() maxLength 60
    var contentText by string()
}