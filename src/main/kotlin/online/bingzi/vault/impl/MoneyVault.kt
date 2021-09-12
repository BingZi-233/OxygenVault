package online.bingzi.vault.impl

import net.milkbowl.vault.economy.EconomyResponse
import online.bingzi.vault.Money
import org.bukkit.entity.Player
import taboolib.platform.compat.VaultService

class MoneyVault : Money {
    private val api = VaultService.economy

    override fun get(player: Player): Double {
        return api?.getBalance(player) ?: 0.0
    }

    override fun add(player: Player, amount: Double): Boolean {
        if (api != null) {
            api.bankDeposit(player.name, amount)
            return true
        }
        return false
    }

    override fun remove(player: Player, amount: Double): Boolean {
        if (api != null) {
            if (api.has(player, amount)) {
                val bankWithdraw = api.bankWithdraw(player.name, amount)
                if (bankWithdraw.type == EconomyResponse.ResponseType.SUCCESS) {
                    return true
                }
            }
        }
        return false
    }
}