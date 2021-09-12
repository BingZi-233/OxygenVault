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
                return when (api.withdrawPlayer(player.name, amount).type) {
                    EconomyResponse.ResponseType.SUCCESS -> {
                        true
                    }
                    EconomyResponse.ResponseType.NOT_IMPLEMENTED -> {
                        false
                    }
                    EconomyResponse.ResponseType.FAILURE -> {
                        false
                    }
                    null -> {
                        false
                    }
                }
            }
        }
        return false
    }
}