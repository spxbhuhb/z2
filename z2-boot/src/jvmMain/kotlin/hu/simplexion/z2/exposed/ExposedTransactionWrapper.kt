package hu.simplexion.z2.exposed

import hu.simplexion.z2.commons.protobuf.ProtoMessage
import hu.simplexion.z2.commons.protobuf.ProtoMessageBuilder
import hu.simplexion.z2.service.NoServiceTransform
import hu.simplexion.z2.service.ServiceContext
import hu.simplexion.z2.service.ServiceImpl
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction

@NoServiceTransform
class ExposedTransactionWrapper(
    val wrappedService: ServiceImpl<*>
) : ServiceImpl<ExposedTransactionWrapper> {

    override var serviceName: String
        get() = wrappedService.serviceName
        set(value) { wrappedService.serviceName = value }

    override fun newInstance(serviceContext: ServiceContext) : ExposedTransactionWrapper {
        return ExposedTransactionWrapper(wrappedService.newInstance(serviceContext))
    }

    override suspend fun dispatch(
        funName: String,
        payload: ProtoMessage,
        response: ProtoMessageBuilder
    ) {
        transaction {
            runBlocking {
                wrappedService.dispatch(funName, payload, response)
            }
        }
    }
}