package online.bingzi.repository.impl

import online.bingzi.bean.Oxygen
import online.bingzi.database.typeimpl.DatabaseLocal
import online.bingzi.database.typeimpl.DatabaseSQL
import online.bingzi.repository.Repository
import online.bingzi.util.Tools.confDatabase
import online.bingzi.util.toSerialization

object OxygenRepository : Repository {
    private val type by lazy {
        if (confDatabase.getBoolean("Enable")) {
            DatabaseSQL()
        } else {
            DatabaseLocal()
        }
    }

    // 获取数据源
    private val dataSource = type.host().createDataSource()

    init {
        type.tableVar().workspace(dataSource) { createTable() }.run()
    }

    override operator fun get(username: String): Oxygen? {
        return type.tableVar().workspace(dataSource) {
            select {
                rows("username", "data", "count")
                limit(1)
                where {
                    "username" eq username
                }
            }
        }.firstOrNull {
            Oxygen(
                getString("username"),
                getString("data"),
                getInt("count")
            )
        }
    }

    override fun set(user: Oxygen): Boolean {
        if (get(user.username) == null) {
            type.tableVar().workspace(dataSource) {
                insert("username", "data", "count") {
                    value(user.username, user.itemStacks.toSerialization(), user.count)
                }
            }.run()
        } else {
            type.tableVar().workspace(dataSource) {
                update {
                    where {
                        "username" eq user.username
                    }
                    set("data", user.itemStacks.toSerialization())
                    set("count", user.count)
                }
            }.run()
        }
        return true
    }
}