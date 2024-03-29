/*
 * Copyright © 2022-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.kotlin.services

import hu.simplexion.z2.kotlin.util.LocalName
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

object Strings {
    const val SERVICE_INTERFACE = "Service"
    const val SERVICE_INTERFACE_FQ = "hu.simplexion.z2.services.Service"
    const val SERVICE_NAME_PROPERTY = "serviceName"
    const val SERVICE_CALL_TRANSPORT_OR_DEFAULT = "serviceCallTransportOrDefault"
    const val SERVICE_CALL_TRANSPORT_PROPERTY = "serviceCallTransport"
    const val SERVICE_CALL_TRANSPORT = "ServiceCallTransport"
    const val SERVICES_TRANSPORT_PACKAGE = "hu.simplexion.z2.services.transport"
}

object Names {
    val SERVICE_INTERFACE = Name.identifier(Strings.SERVICE_INTERFACE)
    val SERVICE_NAME_PROPERTY = Name.identifier(Strings.SERVICE_NAME_PROPERTY)
    val SERVICE_CALL_TRANSPORT_OR_DEFAULT = Name.identifier(Strings.SERVICE_CALL_TRANSPORT_OR_DEFAULT)
    val SERVICE_CALL_TRANSPORT_PROPERTY = Name.identifier(Strings.SERVICE_CALL_TRANSPORT_PROPERTY)
    val SERVICE_CALL_TRANSPORT = Name.identifier(Strings.SERVICE_CALL_TRANSPORT)
}

object FqNames {
    val SERVICE_INTERFACE = FqName.fromSegments(Strings.SERVICE_INTERFACE_FQ.split('.'))
    val SERVICES_TRANSPORT_PACKAGE = FqName.fromSegments(Strings.SERVICES_TRANSPORT_PACKAGE.split('.'))
}

object ClassIds {
    val SERVICE_CALL_TRANSPORT = ClassId(FqNames.SERVICES_TRANSPORT_PACKAGE, Names.SERVICE_CALL_TRANSPORT)
}

val Name.serviceConsumerName get() = Name.identifier("${this.identifier}\$Consumer")

// before reorg 2024.02.26

const val RUNTIME_PACKAGE = "hu.simplexion.z2.services"

const val SERVICE_CLASS = "Service"

const val CONSUMER_POSTFIX = "\$Consumer"
const val SERVICE_IMPL_CLASS = "ServiceImpl"
const val SERVICE_CONSUMER_CLASS = "ServiceConsumer"

val SERVICE_IMPL_FQ_NAME = FqName("$RUNTIME_PACKAGE.$SERVICE_IMPL_CLASS")

const val TRANSPORT_PACKAGE = "$RUNTIME_PACKAGE.transport"
const val GLOBALS_CLASS = "GlobalsKt"
const val DEFAULT_SERVICE_CALL_TRANSPORT = "defaultServiceCallTransport"
const val GET_SERVICE = "getService"
const val SERVICE_IMPL_NEW_INSTANCE = "newInstance"

// FIXME service API function new should not be directly skipped
val FUN_NAMES_TO_SKIP = listOf("new", "equals", "hashCode", "toString", Strings.SERVICE_CALL_TRANSPORT_OR_DEFAULT)
val SERVICE_CONTEXT_PROPERTY = LocalName("serviceContext")
const val SERVICE_CONTEXT_ARG_NAME = "serviceContext"
const val SERVICE_CONTEXT_CLASS = "ServiceContext"

const val DISPATCH_NAME = "dispatch"
const val DISPATCH_FUN_NAME_INDEX = 0
const val DISPATCH_PAYLOAD_INDEX = 1
const val DISPATCH_RESPONSE_INDEX = 2

const val SERVICE_CALL_TRANSPORT_CLASS = "ServiceCallTransport"
const val CALL_FUNCTION = "call"
const val CALL_TYPE_INDEX = 0 // type argument index for CALL_FUNCTION, this is the return type of the service call
const val CALL_SERVICE_NAME_INDEX = 0
const val CALL_FUN_NAME_INDEX = 1
const val CALL_PAYLOAD_INDEX = 2
const val CALL_DECODER_INDEX = 3

const val SIGNATURE_BOOLEAN = "Z"
const val SIGNATURE_INT = "I"
const val SIGNATURE_LONG = "J"
const val SIGNATURE_STRING = "S"
const val SIGNATURE_BYTEARRAY = "[B"
const val SIGNATURE_UUID = "U"
const val SIGNATURE_INSTANCE = "L";

const val SIGNATURE_LIST = "*"
const val SIGNATURE_DELIMITER = ";"
const val SIGNATURE_UNKNOWN = "?"

const val SIGNATURE_BOOLEAN_LIST = "*Z"
const val SIGNATURE_INT_LIST = "*I"
const val SIGNATURE_LONG_LIST = "*J"
const val SIGNATURE_STRING_LIST = "*S"
const val SIGNATURE_BYTEARRAY_LIST = "*[B"
const val SIGNATURE_UUID_LIST = "*U"

const val PROTO_PACKAGE = "hu.simplexion.z2.serialization.protobuf"

const val PROTO_ONE_UNIT = "ProtoOneUnit"

const val PROTO_MESSAGE_BUILDER_CLASS = "ProtoMessageBuilder"
const val PROTO_MESSAGE_BUILDER_PACK = "pack"
const val PROTO_MESSAGE_CLASS = "ProtoMessage"

const val PROTO_ENCODER_CLASS = "ProtoEncoder"
const val PROTO_DECODER_CLASS = "ProtoDecoder"

const val TIME_PACKAGE = "kotlin.time"
const val TIME_DURATION = "Duration"

const val DATETIME_PACKAGE = "kotlinx.datetime"
const val DATETIME_INSTANT = "Instant"
const val DATETIME_LOCAL_DATE = "LocalDate"
const val DATETIME_LOCAL_DATE_TIME = "LocalDateTime"

const val PROTO_DURATION = "ProtoDuration"
const val PROTO_INSTANT = "ProtoInstant"
const val PROTO_LOCAL_DATE = "ProtoLocalDate"
const val PROTO_LOCAL_DATE_TIME = "ProtoLocalDateTime"

