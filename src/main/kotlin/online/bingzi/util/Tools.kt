package online.bingzi.util

import online.bingzi.vault.impl.MoneyPlayerPoints
import online.bingzi.vault.impl.MoneyVault
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import taboolib.module.configuration.util.getStringColored
import taboolib.platform.util.ItemBuilder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

@Awake(LifeCycle.ACTIVE)
val moneyVault = MoneyVault()

@Awake(LifeCycle.ACTIVE)
val moneyPlayerPoints = MoneyPlayerPoints()

fun <T> T.fromSerialization(): Any {
    if (this is String) {
        ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
            BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
                return bukkitObjectInputStream.readObject()
            }
        }
    } else {
        return ""
    }
}

fun <T> T.toSerialization(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

object Tools {
    /**
     * database config file
     */
    @Config(value = "database.yml")
    lateinit var confDatabase: SecuredFile
        private set

    /**
     * config file
     */
    @Config(value = "config.yml")
    lateinit var conf: SecuredFile
        private set

    /**
     * View config file
     */
    @Config(value = "view.yml")
    lateinit var confView: SecuredFile
        private set

    /**
     * 用于快速构建出一个合适的物品
     * @return 成品
     */
    fun String.builderItem(): ItemStack {
        return ItemBuilder(
            XMaterial.matchXMaterial(confView.getString("Button.${this}.display.mats")).get()
        ).also {
            it.name = confView.getStringColored("Button.${this}.display.Name")?.colored()
            it.lore.addAll(builderLore(confView.getStringList("Button.${this}.display.Lore")))
            confView.getStringList("Button.${this}.flag").forEach { s ->
                it.flags.add(ItemFlag.valueOf(s))
            }
            if (confView.getBoolean("Button.${this}.shiny")) it.shiny()
            it.customModelData = confView.getInt("Button.${this}.CustomModelData")
            it.colored()
        }.build()
    }

    /**
     * Lore渐变支持
     * @param list Lore
     * @return 渐变Lore
     */
    private fun builderLore(list: MutableList<String>): MutableList<String> {
        val newList = mutableListOf<String>()
        list.forEach {
            newList.add(it.colored())
        }
        return newList
    }
}