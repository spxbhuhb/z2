package hu.simplexion.z2.localization.api

import hu.simplexion.z2.util.UUID
import hu.simplexion.z2.localization.model.Locale
import hu.simplexion.z2.localization.model.Translation
import hu.simplexion.z2.services.Service

interface TranslationApi : Service {

    suspend fun list(locale : UUID<Locale>): List<Translation>

    suspend fun put(translation: Translation)

    suspend fun remove(key: String, locale : UUID<Locale>? = null)

}