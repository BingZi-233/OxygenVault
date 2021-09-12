package online.bingzi.view.viewimpl

import online.bingzi.bean.Oxygen
import online.bingzi.repository.impl.OxygenRepository
import online.bingzi.util.Tools.builderItem
import online.bingzi.util.Tools.confView
import online.bingzi.view.View
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.module.chat.colored
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.compat.replacePlaceholder

object MainView : View {
    override fun open(sender: Player, username: String) {
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
            // 取出物品
            onClick { event, element ->
                event.isCancelled = true
                val rawSlot = event.rawSlot
                // 判断钱够不够
                event.inventory.setItem(rawSlot, ItemStack(Material.AIR))
                event.clicker.inventory.addItem(element)
                get.deleteItemStack(element)
                OxygenRepository.set(get)
            }
            // 打开上传界面
            onClick { it ->
                val rawSlot = it.rawSlot
                if (rawSlot >= 54 || rawSlot == -999) {
                    UploadView.open(sender, username)
                }
            }
            onClose {
                OxygenRepository.set(get)
            }
        }
    }
}