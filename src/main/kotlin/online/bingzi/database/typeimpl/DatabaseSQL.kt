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
            type(ColumnTypeSQL.VARCHAR, 64) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("data") {
            type(ColumnTypeSQL.TEXT)
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