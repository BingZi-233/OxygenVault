package online.bingzi.database.typeimpl

import online.bingzi.database.Database
import online.bingzi.util.Tools.confDatabase
import taboolib.module.database.*

/**
 * MySQL链接
 */
class DatabaseSQL : Database() {
    private val host = confDatabase.getHost("MySQL")
    private val tableVar = Table("${confDatabase.getString("MySQL.TablePrefix")}_player_oxygen", host) {
        add { id() }
        // 唯一主键
        add("username") {
            type(ColumnTypeSQL.TEXT, 32) {
                options(ColumnOptionSQL.UNIQUE_KEY)
            }
        }
        add("data") {
            type(ColumnTypeSQL.TEXT, 65535)
        }
        add("count") {
            type(ColumnTypeSQL.INT)
        }
    }

    override fun host(): Host<*> {
        return host
    }

    override fun tableVar(): Table<*, *> {
        return tableVar
    }
}