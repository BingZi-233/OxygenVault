package online.bingzi.vault

import org.bukkit.entity.Player

/**
 * 经济接口
 */
interface Money {
    /**
     * 获取玩家金额
     * @param player 玩家
     * @return 金额
     */
    fun get(player: Player): Double

    /**
     * 给指定玩家增加金额
     * @param player 玩家
     * @param amount 金额
     * @return 是否成功
     */
    fun add(player: Player, amount: Double): Boolean

    /**
     * 给指定玩家减少金额
     * @param player 玩家
     * @param amount 金额
     * @return 是否成功
     */
    fun remove(player: Player, amount: Double): Boolean
}