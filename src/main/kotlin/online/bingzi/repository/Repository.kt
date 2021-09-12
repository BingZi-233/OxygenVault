package online.bingzi.repository

import online.bingzi.bean.Oxygen

interface Repository {
    /**
     * 获取用户的仓库物品数据
     */
    fun get(username: String): Oxygen?

    /**
     * 设置用户的仓库物品数据
     */
    fun set(user: Oxygen): Boolean
}