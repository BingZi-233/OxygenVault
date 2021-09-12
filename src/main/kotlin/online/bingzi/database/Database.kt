package online.bingzi.database

import taboolib.module.database.Host
import taboolib.module.database.Table

abstract class Database {

    abstract fun host(): Host<*>

    abstract fun tableVar(): Table<*, *>
}