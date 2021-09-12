package online.bingzi.database.typeimpl

import online.bingzi.database.Database
import taboolib.common.io.newFile
import taboolib.common.platform.function.getDataFolder
import taboolib.module.database.*

/**
 * SQLite链接
 */
class DatabaseLocal : Database() {
    private val host = newFile(getDataFolder(), "data.db").getHost()
    private val tableVar = Table("player_oxygen", host) {
        // 唯一主键
        add("username") {
            type(ColumnTypeSQLite.TEXT, 32) {
                options(ColumnOptionSQLite.PRIMARY_KEY)
            }
        }
        add("data") {
            type(ColumnTypeSQLite.TEXT, 65535)
        }
        add("count") {
            type(ColumnTypeSQLite.NUMERIC)
        }
    }

    override fun host(): Host<*> {
        return host
    }

    override fun tableVar(): Table<*, *> {
        return tableVar
    }
}