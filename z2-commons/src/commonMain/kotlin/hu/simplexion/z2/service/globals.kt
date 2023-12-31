package hu.simplexion.z2.service

import hu.simplexion.z2.commons.util.UUID
import hu.simplexion.z2.service.factory.BasicServiceImplFactory
import hu.simplexion.z2.service.factory.ServiceImplFactory
import hu.simplexion.z2.service.transport.LocalServiceCallTransport
import hu.simplexion.z2.service.transport.ServiceCallTransport

var defaultServiceCallTransport: ServiceCallTransport = LocalServiceCallTransport()

val defaultServiceImplFactory: ServiceImplFactory = BasicServiceImplFactory()

/**
 * Get a service consumer for the interface, specified by the type parameter.
 *
 * **You should NOT pass the [consumer] parameter! It is set by the compiler plugin.**
 *
 * ```kotlin
 * val clicks = getService<ClickApi>()
 * ```
 */
fun <T : Service> getService(consumer: T? = null): T {
    return checkNotNull(consumer)
}

/**
 * Get a typed data instance fom the service context.
 *
 * @throws  ClassCastException  If the instance is not of the class [T].
 */
inline operator fun <reified T> ServiceContext.get(uuid: UUID<T>): T? {
    return data.let { it[uuid] } as? T
}

/**
 * Put a data instance into the service context.
 */
operator fun <T> ServiceContext.set(uuid: UUID<T>, value: T) {
    data.let { it[uuid] = value }
}