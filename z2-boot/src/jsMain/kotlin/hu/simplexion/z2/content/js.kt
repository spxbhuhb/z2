package hu.simplexion.z2.content

import hu.simplexion.z2.content.api.ContentApi
import hu.simplexion.z2.content.api.ContentTypeApi
import hu.simplexion.z2.service.getService

val contentService = getService<ContentApi>()
val contentTypeService = getService<ContentTypeApi>()

fun contentJs() {
    contentCommon()
}