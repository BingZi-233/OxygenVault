package online.bingzi.vault.impl

import online.bingzi.vault.Money
import org.black_ixx.playerpoints.PlayerPoints
import org.bukkit.entity.Player

class MoneyPlayerPoints : Money {
    private val api = PlayerPoints.getInstance().api

    override fun get(player: Player): Double {
        return api?.look(player.uniqueId)?.toDouble() ?: 0.0
    }

    override fun add(player: Player, amount: Double): Boolean {
        if (api != null) {
            return api.give(player.uniqueId, amount.toInt())
        }
        return false
    }

    override fun remove(player: Player, amount: Double): Boolean {
        if (api != null) {
            return api.take(player.uniqueId, amount.toInt())
        }
        return false
    }
}