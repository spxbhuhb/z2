/*
 * Copyright © 2020-2024, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.adaptive.kotlin.adat

import hu.simplexion.adaptive.kotlin.common.NamesBase
import org.jetbrains.kotlin.name.FqName

object Strings {
    const val RUNTIME_PACKAGE = "hu.simplexion.adaptive.adat"
    const val METADATA_PACKAGE = "hu.simplexion.adaptive.adat.metadata"
}

object Names : NamesBase(Strings.RUNTIME_PACKAGE) {
    val ADAT_VALUES = "adatValues".name()
    val ADAT_METADATA = "adatMetaData".name()
    val ADAT_WIREFORMAT = "adatWireFormat".name()
    val NEW_INSTANCE = "newInstance".name()
}

object FqNames {
    val ADAT_ANNOTATION = FqName("hu.simplexion.adaptive.adat.Adat")
}

object ClassIds : NamesBase(Strings.RUNTIME_PACKAGE) {
    val ADAT_COMPANION = "AdatCompanion".classId()

    val ADAT_CLASS_METADATA = "AdatClassMetaData".classId { Strings.METADATA_PACKAGE.fqName() }

    val KOTLIN_ARRAY= "kotlin.Array".classId()
    val KOTLIN_ANY = "kotlin.Any".classId()
}

object CallableIds : NamesBase(Strings.RUNTIME_PACKAGE) {

}

object Indices {

}