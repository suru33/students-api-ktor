import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import kotlinx.coroutines.Dispatchers
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("db.driver").getString()
        val database = config.property("db.database").getString()
        val jdbcUrl = config.property("db.url").getString()
        val user = config.property("db.user").getString()
        val password = config.property("db.password").getString()

        val connectionPool = createHikariDataSource(
            url = "$jdbcUrl/$database?user=$user&password=$password",
            driver = driverClassName
        )

        Database.connect(connectionPool)
        val flyway = Flyway.configure().dataSource("$jdbcUrl/$database", user, password).load()
        flyway.migrate()
    }

    private fun createHikariDataSource(
        url: String,
        driver: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}