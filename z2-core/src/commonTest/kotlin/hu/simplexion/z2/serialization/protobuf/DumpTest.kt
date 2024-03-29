package hu.simplexion.z2.serialization.protobuf

import hu.simplexion.z2.serialization.protobuf.ProtoMessageBuilder
import hu.simplexion.z2.serialization.protobuf.dumpProto
import kotlin.test.Test

class DumpTest {

    @Test
    fun testDump() {
        val dump = ProtoMessageBuilder()
            .int(1, 12)
            .string(2, "Hello")
            .instance(3, A, A(false, 123, "World", mutableListOf(12)))
            .pack()
            .dumpProto()

        println(dump)
    }
}