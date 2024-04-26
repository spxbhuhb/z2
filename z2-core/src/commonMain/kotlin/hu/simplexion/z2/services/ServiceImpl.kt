package hu.simplexion.z2.services

import hu.simplexion.z2.utility.UUID
import hu.simplexion.z2.utility.pluginGenerated
import hu.simplexion.z2.wireformat.WireFormatDecoder

interface ServiceImpl<T : ServiceImpl<T>> : Service {

    /**
     * Context of a service call. Set by `dispatch` when the call goes through it.
     */
    val serviceContext: ServiceContext
        get() {
            throw IllegalStateException("ServiceContext should be overridden manually or by the compiler plugin, is the plugin missing?")
        }

    /**
     * The internal version of this service implementation. The context is [BasicServiceContext] with
     * `UUID.NIL` context id.
     */
    val internal: T
        get() = newInstance(BasicServiceContext(UUID.nil()))

    /**
     * Create a new instance of the given service with the given context.
     */
    operator fun invoke(context: ServiceContext): T = newInstance(context)

    fun newInstance(serviceContext: ServiceContext): T {
        throw IllegalStateException("newInstance should be overridden by the compiler plugin, is tha plugin missing?")
    }

    fun unknownFunction(funName: String): Nothing {
        throw IllegalStateException("unknown function: $funName")
    }

    /**
     * Called by service transports to execute a service call. Actual code of this function is generated
     * by the plugin.
     */
    suspend fun dispatch(funName: String, payload: WireFormatDecoder<*>): ByteArray {
        pluginGenerated()
    }

}