package hu.simplexion.z2.site.boot

import hu.simplexion.z2.IBaseStrings
import hu.simplexion.z2.application.applicationJs
import hu.simplexion.z2.auth.authJs
import hu.simplexion.z2.auth.model.Session
import hu.simplexion.z2.auth.sessionService
import hu.simplexion.z2.baseStrings
import hu.simplexion.z2.browser.IBrowserStrings
import hu.simplexion.z2.browser.browserStrings
import hu.simplexion.z2.browser.field.FieldStyle
import hu.simplexion.z2.browser.material.textfield.FieldConfig.Companion.defaultFieldStyle
import hu.simplexion.z2.browser.routing.effectiveRoles
import hu.simplexion.z2.browser.routing.isLoggedIn
import hu.simplexion.z2.history.historyJs
import hu.simplexion.z2.ktor.BasicWebSocketServiceCallTransport
import hu.simplexion.z2.localization.LocalizationProvider
import hu.simplexion.z2.localization.localeJs
import hu.simplexion.z2.localization.text.ICommonStrings
import hu.simplexion.z2.localization.text.IDateTimeStrings
import hu.simplexion.z2.localization.text.commonStrings
import hu.simplexion.z2.localization.text.dateTimeStrings
import hu.simplexion.z2.schematic.schema.validation.IValidationStrings
import hu.simplexion.z2.schematic.schema.validation.validationStrings
import hu.simplexion.z2.services.defaultServiceCallTransport
import io.ktor.client.fetch.*
import kotlinx.browser.window
import kotlinx.coroutines.await

var session = Session()
    set(value) {
        field = value
        isLoggedIn = (session.principal != null)
        effectiveRoles = value.roles
    }

suspend fun bootJs(
    fieldStyle : FieldStyle? = null,
    localizedStrings : LocalizationProvider? = null
) {
    fetch("/z2/session").await()

    defaultServiceCallTransport = BasicWebSocketServiceCallTransport(
        window.location.origin.replace("http", "ws") + "/z2/services",
        SnackbarServiceErrorHandler(),
        false
    ).also {
        it.start()
    }

    applicationJs()
    localeJs()
    authJs()
    historyJs()

    if (localizedStrings is IDateTimeStrings) dateTimeStrings = localizedStrings
    if (localizedStrings is IBrowserStrings) browserStrings = localizedStrings
    if (localizedStrings is ICommonStrings) commonStrings = localizedStrings
    if (localizedStrings is IBaseStrings) baseStrings = localizedStrings
    if (localizedStrings is IValidationStrings) validationStrings = localizedStrings

    fieldStyle?.let { defaultFieldStyle = it }

    sessionService.getSession()?.let { session = it }

    isLoggedIn = (session.principal != null)
    effectiveRoles = session.roles
}