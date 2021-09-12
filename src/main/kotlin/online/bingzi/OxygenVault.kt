package online.bingzi

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info

object OxygenVault : Plugin() {

    override fun onLoad() {
        info("Oxygen Vault plugin in loading...")

        info("Oxygen Vault plugin load is ok!")
    }

    override fun onEnable() {
        info("Oxygen Vault plugin in enabling...")
        info("Oxygen Vault plugin enable is ok!")
    }

    override fun onDisable() {
        info("Oxygen Vault plugin in disabling...")
        info("Oxygen Vault plugin disable is ok!")
    }
}