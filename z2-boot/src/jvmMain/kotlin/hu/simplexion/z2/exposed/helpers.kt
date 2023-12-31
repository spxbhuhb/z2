package hu.simplexion.z2.exposed

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import hu.simplexion.z2.commons.util.UUID
import hu.simplexion.z2.service.ServiceImpl
import hu.simplexion.z2.service.defaultServiceImplFactory
import hu.simplexion.z2.setting.util.mandatoryEnvString
import org.apache.logging.log4j.core.config.Configurator
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

fun dbFromEnvironment() {
    val config = HikariConfig().apply {
        jdbcUrl         = mandatoryEnvString("DB_JDBC_URL")
        driverClassName = mandatoryEnvString("DB_DRIVER_CLASS_NAME")
        username        = mandatoryEnvString("DB_USER")
        password        = mandatoryEnvString("DB_PASSWORD")
        maximumPoolSize = 10
    }

    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
}

fun h2Test(vararg tables: Table) {
    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables)
    }
}

fun debugExposed(active: Boolean) {
    val logger = LoggerFactory.getLogger("Exposed") as Logger
    logger.level = if (active) Level.DEBUG else Level.WARN
}

fun logOnlyWarnings() {
    val rootLogger = java.util.logging.Logger.getLogger("")
    rootLogger.setLevel(java.util.logging.Level.WARNING)
    for (h in rootLogger.handlers) {
        h.setLevel(java.util.logging.Level.WARNING)
    }

    val rootLogger2 = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as Logger
    rootLogger2.setLevel(Level.WARN)

    Configurator.setRootLevel(org.apache.logging.log4j.Level.WARN)
}

inline fun withTransaction(wrappedService: () -> ServiceImpl<*>) =
    ExposedTransactionWrapper(wrappedService())

fun <T> EntityID<java.util.UUID>.z2() =
    UUID<T>(this.value.mostSignificantBits, this.value.leastSignificantBits)

fun <T> java.util.UUID.z2() =
    UUID<T>(this.mostSignificantBits, this.leastSignificantBits)

val UUID<*>.jvm : java.util.UUID
    get() = java.util.UUID(this.msb, this.lsb)

fun tables(vararg tables : Table) {
    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables)
    }
}

fun implementations(vararg implementations : ServiceImpl<*>) {
    for (implementation in implementations) {
        defaultServiceImplFactory += withTransaction { implementation }
    }
}

fun Table.isEmpty() = (selectAll().count() == 0L)

fun Table.isNotEmpty() = (selectAll().count() != 0L)

fun Boolean.alsoTransactionIf(block : (it : Boolean) -> Unit) : Boolean {
    transaction { block(this@alsoTransactionIf) }
    return this
}