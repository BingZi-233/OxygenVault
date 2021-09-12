package online.bingzi.vault.impl

import online.bingzi.vault.Money
import org.black_ixx.playerpoints.PlayerPoints
import org.bukkit.entity.Player

class MoneyPlayerPoints : Money {
    private val api = PlayerPoints.getInstance().api

    override fun get(player: Player): Double {
        return api?.lookAsync(player.uniqueId)?.get()?.toDouble() ?: 0.0
    }

    override fun add(player: Player, amount: Double): Boolean {
        if (api != null) {
            return api.giveAsync(player.uniqueId, amount.toInt()).get()
        }
        return false
    }

    override fun remove(player: Player, amount: Double): Boolean {
        if (api != null) {
            return api.takeAsync(player.uniqueId, amount.toInt()).get()
        }
        return false
    }
}