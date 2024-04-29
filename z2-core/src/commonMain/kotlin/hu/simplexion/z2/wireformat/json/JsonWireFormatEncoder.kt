package hu.simplexion.z2.wireformat.json

import hu.simplexion.z2.utility.UUID
import hu.simplexion.z2.wireformat.WireFormat
import hu.simplexion.z2.wireformat.WireFormatEncoder
import hu.simplexion.z2.wireformat.WireFormatKind
import kotlin.enums.EnumEntries

/**
 * Build JSON messages.
 *
 * Use the type-specific functions to add records and then use [pack] to get
 * the wire format message.
 */
@OptIn(ExperimentalUnsignedTypes::class)
class JsonWireFormatEncoder(
    private val writer: JsonBufferWriter = JsonBufferWriter()
) : WireFormatEncoder {

    override fun pack() = writer.pack()

    // ----------------------------------------------------------------------------
    // Any
    // ----------------------------------------------------------------------------

    override fun any(fieldNumber: Int, fieldName: String, value: Any): JsonWireFormatEncoder {
        TODO()
    }

    override fun anyOrNull(fieldNumber: Int, fieldName: String, value: Any?): JsonWireFormatEncoder {
        TODO()
    }

    override fun rawAny(value: Any): WireFormatEncoder {
        TODO("Not yet implemented")
    }

    // ----------------------------------------------------------------------------
    // Unit
    // ----------------------------------------------------------------------------

    override fun unit(fieldNumber: Int, fieldName: String, value: Unit): JsonWireFormatEncoder {
        writer.bool(fieldName, true)
        return this
    }

    override fun unitOrNull(fieldNumber: Int, fieldName: String, value: Unit?): JsonWireFormatEncoder {
        writer.bool(fieldName, if (value != null) true else null)
        return this
    }

    override fun rawUnit(value: Unit): WireFormatEncoder {
        writer.rawBool(true)
        return this
    }

    // ----------------------------------------------------------------------------
    // Boolean
    // ----------------------------------------------------------------------------

    override fun boolean(fieldNumber: Int, fieldName: String, value: Boolean): JsonWireFormatEncoder {
        writer.bool(fieldName, value)
        return this
    }

    override fun booleanOrNull(fieldNumber: Int, fieldName: String, value: Boolean?): JsonWireFormatEncoder {
        writer.bool(fieldName, value)
        return this
    }

    override fun rawBoolean(value: Boolean): WireFormatEncoder {
        writer.rawBool(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Int
    // ----------------------------------------------------------------------------

    override fun int(fieldNumber: Int, fieldName: String, value: Int): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun intOrNull(fieldNumber: Int, fieldName: String, value: Int?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawInt(value: Int): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Short
    // ----------------------------------------------------------------------------

    override fun short(fieldNumber: Int, fieldName: String, value: Short): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun shortOrNull(fieldNumber: Int, fieldName: String, value: Short?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawShort(value: Short): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Byte
    // ----------------------------------------------------------------------------

    override fun byte(fieldNumber: Int, fieldName: String, value: Byte): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun byteOrNull(fieldNumber: Int, fieldName: String, value: Byte?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawByte(value: Byte): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Long
    // ----------------------------------------------------------------------------

    override fun long(fieldNumber: Int, fieldName: String, value: Long): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun longOrNull(fieldNumber: Int, fieldName: String, value: Long?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawLong(value: Long): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Float
    // ----------------------------------------------------------------------------

    override fun float(fieldNumber: Int, fieldName: String, value: Float): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun floatOrNull(fieldNumber: Int, fieldName: String, value: Float?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawFloat(value: Float): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Double
    // ----------------------------------------------------------------------------

    override fun double(fieldNumber: Int, fieldName: String, value: Double): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun doubleOrNull(fieldNumber: Int, fieldName: String, value: Double?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawDouble(value: Double): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Char
    // ----------------------------------------------------------------------------

    override fun char(fieldNumber: Int, fieldName: String, value: Char): JsonWireFormatEncoder {
        writer.string(fieldName, value.toString())
        return this
    }

    override fun charOrNull(fieldNumber: Int, fieldName: String, value: Char?): JsonWireFormatEncoder {
        writer.string(fieldName, value?.toString())
        return this
    }

    override fun rawChar(value: Char): WireFormatEncoder {
        writer.quotedString(value.toString())
        return this

    }

    // ----------------------------------------------------------------------------
    // BooleanArray
    // ----------------------------------------------------------------------------

    override fun booleanArray(fieldNumber: Int, fieldName: String, value: BooleanArray): JsonWireFormatEncoder {
        booleanArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun booleanArrayOrNull(fieldNumber: Int, fieldName: String, value: BooleanArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawBool(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawBooleanArray(value: BooleanArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawBool(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // IntArray
    // ----------------------------------------------------------------------------

    override fun intArray(fieldNumber: Int, fieldName: String, value: IntArray): JsonWireFormatEncoder {
        intArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun intArrayOrNull(fieldNumber: Int, fieldName: String, value: IntArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawIntArray(value: IntArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // ShortArray
    // ----------------------------------------------------------------------------

    override fun shortArray(fieldNumber: Int, fieldName: String, value: ShortArray): JsonWireFormatEncoder {
        shortArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun shortArrayOrNull(fieldNumber: Int, fieldName: String, value: ShortArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawShortArray(value: ShortArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // ByteArray
    // ----------------------------------------------------------------------------

    override fun byteArray(fieldNumber: Int, fieldName: String, value: ByteArray): JsonWireFormatEncoder {
        writer.bytes(fieldName, value)
        return this
    }

    override fun byteArrayOrNull(fieldNumber: Int, fieldName: String, value: ByteArray?): JsonWireFormatEncoder {
        writer.bytes(fieldName, value)
        return this
    }

    override fun rawByteArray(value: ByteArray): WireFormatEncoder {
        array(null, value) {
            writer.rawBytes(value)
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // LongArray
    // ----------------------------------------------------------------------------

    override fun longArray(fieldNumber: Int, fieldName: String, value: LongArray): JsonWireFormatEncoder {
        longArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun longArrayOrNull(fieldNumber: Int, fieldName: String, value: LongArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawLongArray(value: LongArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }
    // ----------------------------------------------------------------------------
    // FloatArray
    // ----------------------------------------------------------------------------

    override fun floatArray(fieldNumber: Int, fieldName: String, value: FloatArray): JsonWireFormatEncoder {
        floatArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun floatArrayOrNull(fieldNumber: Int, fieldName: String, value: FloatArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawFloatArray(value: FloatArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }
    // ----------------------------------------------------------------------------
    // DoubleArray
    // ----------------------------------------------------------------------------

    override fun doubleArray(fieldNumber: Int, fieldName: String, value: DoubleArray): JsonWireFormatEncoder {
        doubleArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun doubleArrayOrNull(fieldNumber: Int, fieldName: String, value: DoubleArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawDoubleArray(value: DoubleArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // CharArray
    // ----------------------------------------------------------------------------

    override fun charArray(fieldNumber: Int, fieldName: String, value: CharArray): JsonWireFormatEncoder {
        charArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun charArrayOrNull(fieldNumber: Int, fieldName: String, value: CharArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawString(item.toString())
                writer.separator()
            }
        }
        return this
    }

    override fun rawCharArray(value: CharArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawString(item.toString())
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // String
    // ----------------------------------------------------------------------------

    override fun string(fieldNumber: Int, fieldName: String, value: String): JsonWireFormatEncoder {
        writer.string(fieldName, value)
        return this
    }

    override fun stringOrNull(fieldNumber: Int, fieldName: String, value: String?): JsonWireFormatEncoder {
        writer.string(fieldName, value)
        return this
    }

    override fun rawString(value: String): WireFormatEncoder {
        writer.quotedString(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // Enum
    // ----------------------------------------------------------------------------

    override fun <E : Enum<E>> enum(fieldNumber: Int, fieldName: String, value: E, entries: EnumEntries<E>): WireFormatEncoder {
        enumOrNull(fieldNumber, fieldName, value, entries)
        return this
    }

    override fun <E : Enum<E>> enumOrNull(fieldNumber: Int, fieldName: String, value: E?, entries: EnumEntries<E>): WireFormatEncoder {
        if (value == null) {
            writer.nullValue(fieldName)
        } else {
            writer.fieldName(fieldName)
            writer.quotedString(value.name)
        }
        return this
    }

    override fun <E : Enum<E>> rawEnum(value: E, entries: EnumEntries<E>): WireFormatEncoder {
        writer.quotedString(value.name)
        return this
    }

    // ----------------------------------------------------------------------------
    // UUID
    // ----------------------------------------------------------------------------

    override fun uuid(fieldNumber: Int, fieldName: String, value: UUID<*>): JsonWireFormatEncoder {
        writer.uuid(fieldName, value)
        return this
    }

    override fun uuidOrNull(fieldNumber: Int, fieldName: String, value: UUID<*>?): JsonWireFormatEncoder {
        writer.uuid(fieldName, value)
        return this
    }

    override fun rawUuid(value: UUID<*>): WireFormatEncoder {
        writer.rawUuid(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // UInt
    // ----------------------------------------------------------------------------

    override fun uInt(fieldNumber: Int, fieldName: String, value: UInt): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun uIntOrNull(fieldNumber: Int, fieldName: String, value: UInt?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawUInt(value: UInt): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // UShort
    // ----------------------------------------------------------------------------

    override fun uShort(fieldNumber: Int, fieldName: String, value: UShort): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun uShortOrNull(fieldNumber: Int, fieldName: String, value: UShort?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawUShort(value: UShort): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // UByte
    // ----------------------------------------------------------------------------

    override fun uByte(fieldNumber: Int, fieldName: String, value: UByte): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun uByteOrNull(fieldNumber: Int, fieldName: String, value: UByte?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawUByte(value: UByte): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // ULong
    // ----------------------------------------------------------------------------

    override fun uLong(fieldNumber: Int, fieldName: String, value: ULong): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun uLongOrNull(fieldNumber: Int, fieldName: String, value: ULong?): JsonWireFormatEncoder {
        writer.number(fieldName, value)
        return this
    }

    override fun rawULong(value: ULong): WireFormatEncoder {
        writer.rawNumber(value)
        return this
    }

    // ----------------------------------------------------------------------------
    // UIntArray
    // ----------------------------------------------------------------------------

    override fun uIntArray(fieldNumber: Int, fieldName: String, value: UIntArray): JsonWireFormatEncoder {
        uIntArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun uIntArrayOrNull(fieldNumber: Int, fieldName: String, value: UIntArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawUIntArray(value: UIntArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // UShortArray
    // ----------------------------------------------------------------------------

    override fun uShortArray(fieldNumber: Int, fieldName: String, value: UShortArray): JsonWireFormatEncoder {
        uShortArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun uShortArrayOrNull(fieldNumber: Int, fieldName: String, value: UShortArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawUShortArray(value: UShortArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // UByteArray
    // ----------------------------------------------------------------------------

    override fun uByteArray(fieldNumber: Int, fieldName: String, value: UByteArray): JsonWireFormatEncoder {
        uByteArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun uByteArrayOrNull(fieldNumber: Int, fieldName: String, value: UByteArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawUByteArray(value: UByteArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // ULongArray
    // ----------------------------------------------------------------------------

    override fun uLongArray(fieldNumber: Int, fieldName: String, value: ULongArray): JsonWireFormatEncoder {
        uLongArrayOrNull(fieldNumber, fieldName, value)
        return this
    }

    override fun uLongArrayOrNull(fieldNumber: Int, fieldName: String, value: ULongArray?): JsonWireFormatEncoder {
        array(fieldName, value) {
            for (item in value !!) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    override fun rawULongArray(value: ULongArray): WireFormatEncoder {
        array(null, value) {
            for (item in value) {
                writer.rawNumber(item)
                writer.separator()
            }
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // Instance
    // ----------------------------------------------------------------------------

    override fun <T> instance(fieldNumber: Int, fieldName: String, value: T, wireFormat: WireFormat<T>): JsonWireFormatEncoder {
        instanceOrNull(fieldNumber, fieldName, value, wireFormat)
        return this
    }

    override fun <T> instanceOrNull(fieldNumber: Int, fieldName: String, value: T?, wireFormat: WireFormat<T>): JsonWireFormatEncoder {
        if (value == null) {
            writer.nullValue(fieldName)
        } else {
            writer.fieldName(fieldName)
            rawInstance(value, wireFormat)
        }
        return this
    }

    override fun <T> rawInstance(value: T, wireFormat: WireFormat<T>): WireFormatEncoder {

        when (wireFormat.kind) {
            WireFormatKind.Primitive -> Unit
            WireFormatKind.Collection -> writer.openArray()
            WireFormatKind.Instance -> writer.openObject()
        }

        wireFormat.wireFormatEncode(this, value)

        when (wireFormat.kind) {
            WireFormatKind.Primitive -> Unit
            WireFormatKind.Collection -> writer.closeArray()
            WireFormatKind.Instance -> writer.closeObject()
        }

        return this
    }

    // -----------------------------------------------------------------------------------------
    // Utilities for classes that implement `WireFormat`
    // -----------------------------------------------------------------------------------------

    override fun <T> items(value: Collection<T>, itemWireFormat: WireFormat<T>): WireFormatEncoder {
        for (item in value) {
            rawInstance(item, itemWireFormat)
            writer.separator()
        }
        return this
    }

    // ----------------------------------------------------------------------------
    // Utility
    // ----------------------------------------------------------------------------

    fun <T> array(fieldName: String? = null, values: T?, block: (values: T) -> Unit) {
        if (values == null) {
            if (fieldName == null) {
                writer.rawNullValue()
            } else {
                writer.nullValue(fieldName)
            }
            return
        }

        if (fieldName != null) {
            writer.fieldName(fieldName)
        }

        writer.openArray()
        block(values)
        writer.closeArray()
    }

}