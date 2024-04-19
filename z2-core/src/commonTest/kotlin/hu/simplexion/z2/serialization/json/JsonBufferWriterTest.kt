package hu.simplexion.z2.serialization.json

import hu.simplexion.z2.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonBufferWriterTest {

    @Test
    fun primitives() {
        test("false") { bool(false) }
        test("true") { bool(true) }
        test("1") { number(1) }
        test("1.1") { number(1.1) }
        test("-1") { number(- 1) }
        test("abc") { string("abc") }
        test("null") { string(null) }

        val uuidValue = UUID<Any>()
        test("\"$uuidValue\"") { uuid(uuidValue) }

        val bytesValue = byteArrayOf(1, 2, 3)
        test("\"010203\"") { bytes(bytesValue) }
    }

    @Test
    fun fields() {
        test("\"b\":null") { bool("b", null) }
        test("\"b\":true") { bool("b", true) }
        test("\"b\":false") { bool("b", false) }

        test("\"n\":null") { number("n", null) }
        test("\"i\":1") { number("i", 1) }
        test("\"d\":1.1") { number("d", 1.1) }

        test("\"s\":null") { string("s", null) }
        test("\"s\":\"\"") { string("s", "") }
        test("\"s\":\"abc\"") { string("s", "abc") }

        val uuidValue = UUID<Any>()
        test("\"u\":null") { uuid("u", null) }
        test("\"u\":\"$uuidValue\"") { uuid("u", uuidValue) }

        val bytesValue = byteArrayOf(1, 2, 3)
        test("\"b\":null") { bytes("b", null) }
        test("\"b\":\"010203\"") { bytes("b", bytesValue) }
    }

    @Test
    fun testStringEscape() {
        test(""" "s":"\"" """.trim()) { string("s", "\"") }
        test(""" "s":"\\" """.trim()) { string("s", "\\") }
        test(""" "s":"\n" """.trim()) { string("s", "\n") }
        test(""" "s":"\r" """.trim()) { string("s", "\r") }
        test(""" "s":"\t" """.trim()) { string("s", "\t") }
        test(""" "s":"\u0000" """.trim()) { string("s", "\u0000") }
        test(""" "s":"\u0008" """.trim()) { string("s", "\u0008") }
        test(""" "s":" " """.trim()) { string("s", "\u0020") }
        test(""" "s":"árvíztűrő tükörfúrógép" """.trim()) { string("s", "árvíztűrő tükörfúrógép") }
    }

    fun test(expected: String, write: JsonBufferWriter.() -> Unit) {
        val w = JsonBufferWriter()
        w.write()
        assertEquals(expected, w.pack().decodeToString())
    }
}