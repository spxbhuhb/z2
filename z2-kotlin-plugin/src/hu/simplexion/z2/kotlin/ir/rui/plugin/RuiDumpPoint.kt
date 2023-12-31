/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.ir.rui.plugin

import hu.simplexion.z2.kotlin.ir.rui.RuiPluginContext

enum class RuiDumpPoint(
    val optionValue: String
) {
    Before("before"),
    After("after"),
    RuiTree("rui-tree"),
    AirTree("air-tree"),
    KotlinLike("kotlin-like");

    fun dump(ruiPluginContext: RuiPluginContext, dumpFunc: () -> Unit) {
        if (this in ruiPluginContext.dumpPoints) dumpFunc()
    }

    companion object {
        fun optionValues(): List<String> = entries.map { it.optionValue }
        fun fromOption(value: String): RuiDumpPoint? = entries.firstOrNull { it.optionValue == value }
    }
}