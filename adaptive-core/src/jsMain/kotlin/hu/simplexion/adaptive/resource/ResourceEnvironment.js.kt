/*
 * Copyright 2020-2024 JetBrains s.r.o. and respective authors and developers.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 *
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 *
 * This code has been copied from Compose Multiplatform: https://github.com/JetBrains/compose-multiplatform
 */

package hu.simplexion.adaptive.resource

import kotlinx.browser.window

private external class Intl {
    class Locale(locale: String) {
        val language: String
        val region: String
    }
}

internal actual fun getSystemEnvironment(): ResourceEnvironment {
    val locale = Intl.Locale(window.navigator.language)
    val isDarkTheme = window.matchMedia("(prefers-color-scheme: dark)").matches
    //96 - standard browser DPI https://developer.mozilla.org/en-US/docs/Web/API/Window/devicePixelRatio
    val dpi: Int = (window.devicePixelRatio * 96).toInt()
    return ResourceEnvironment(
        language = LanguageQualifier(locale.language),
        region = RegionQualifier(locale.region),
        theme = ThemeQualifier.selectByValue(isDarkTheme),
        density = DensityQualifier.selectByValue(dpi)
    )
}