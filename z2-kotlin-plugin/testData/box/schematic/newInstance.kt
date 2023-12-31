package foo.bar

import hu.simplexion.z2.schematic.Schematic
import hu.simplexion.z2.schematic.SchematicCompanion

class Test : Schematic<Test>() {

    var intField by int(default = 5)

    companion object : SchematicCompanion<Test>
}

fun box(): String {
    val test = Test.newInstance()
    if (test.intField != 5) return "Fail"
    return "OK"
}