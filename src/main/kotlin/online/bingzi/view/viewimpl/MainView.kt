package online.bingzi.view.viewimpl

import online.bingzi.bean.Oxygen
import online.bingzi.repository.impl.OxygenRepository
import online.bingzi.util.Tools.builderItem
import online.bingzi.util.Tools.conf
import online.bingzi.util.Tools.confView
import online.bingzi.util.moneyPlayerPoints
import online.bingzi.util.moneyVault
import online.bingzi.view.View
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.info
import taboolib.module.chat.colored
import taboolib.module.ui.ClickEvent
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.compat.replacePlaceholder
import taboolib.platform.util.giveItem

object MainView : View {
    override fun open(sender: Player, username: String) {
        val unLockButton = "NextEnd".builderItem()
        // 如果获取到的是一个NULL对象，则新建一个Oxygen对象用于玩家数据存储
        val get = OxygenRepository.get(username) ?: Oxygen(username, ArrayList(), 1)
        // 内容区域
        val content =
            mutableListOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34).toList()
        sender.openMenu<Linked<ItemStack>>(confView.getString("Title").colored()) {
            // 一共几行
            rows(6)
            // 可放置物品位置
            slots(content)
            // 元素
            elements {
                get.itemStacks ?: mutableListOf()
            }
            onGenerate { _, element, _, _ ->
                element
            }
            // 下一页位置以及物品
            setNextPage(43) { _, hasNextPage ->
                if (hasNextPage) {
                    "Next".builderItem()
                } else {
                    "NextEnd".builderItem()
                }
            }
            // 上一页位置以及物品
            setPreviousPage(37) { _, hasPreviousPage ->
                if (hasPreviousPage) {
                    "Previous".builderItem()
                } else {
                    "PreviousEnd".builderItem()
                }
            }
            // 设置返回按钮
            set(40, "Back".builderItem()) {
                when (this.clickEvent().click) {
                    ClickType.LEFT -> {
                        back(sender)
                    }
                    ClickType.RIGHT -> {
                        back(sender)
                    }
                    ClickType.SHIFT_LEFT -> {
                        if (moneyVault.remove(sender, conf.getDouble("UnLock.Vault"))) {
                            get.autoAddCount()
                            OxygenRepository.set(get)
                        }
                    }
                    ClickType.SHIFT_RIGHT -> {
                        if (moneyPlayerPoints.remove(sender, conf.getDouble("UnLock.PlayerPoints"))) {
                            get.autoAddCount()
                            OxygenRepository.set(get)
                        }
                    }
                }
            }
            // 取出物品
            onClick { event, element ->
                event.isCancelled = true
                val rawSlot = event.rawSlot
                if (element.type == Material.AIR) return@onClick
                val result = if (event.clickEvent().click.isRightClick) {
                    moneyPlayerPoints.remove(sender, conf.getDouble("RemoveItems.PlayerPoints"))
                } else if (event.clickEvent().click.isLeftClick) {
                    moneyVault.remove(sender, conf.getDouble("RemoveItems.Vault"))
                } else {
                    false
                }
                if (result) {
                    event.inventory.setItem(rawSlot, ItemStack(Material.AIR))
                    event.clicker.giveItem(element)
                    get.deleteItemStack(element)
                    OxygenRepository.set(get)
                    open(sender, username)
                }
            }
            // 打开上传界面
            onClick { it ->
                val rawSlot = it.rawSlot
                info("点击位置为: $rawSlot")
                if (rawSlot >= 54 || rawSlot == -999) {
                    UploadView.open(sender, username)
                }
            }
            onClose {
                OxygenRepository.set(get)
            }
        }
    }

    private fun ClickEvent.back(sender: Player) {
        val commandContent = confView.getStringList("Button.Back.Command.Cmd")
        // 是否以玩家身份执行命令
        if (confView.getBoolean("Button.Back.Command.isPlayer")) {
            commandContent.forEach {
                this.clicker.performCommand(it)
            }
        } else {
            commandContent.forEach {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), it.replacePlaceholder(sender))
            }
        }
    }
}