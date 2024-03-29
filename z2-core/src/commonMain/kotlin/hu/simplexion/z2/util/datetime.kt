package hu.simplexion.z2.util

import kotlinx.datetime.*

fun Instant.here() = toLocalDateTime(TimeZone.currentSystemDefault())

fun hereAndNow() : LocalDateTime = Clock.System.now().here()