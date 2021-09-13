package online.bingzi.view.viewimpl

import online.bingzi.repository.impl.OxygenRepository
import online.bingzi.util.Tools.builderItem
import online.bingzi.util.Tools.conf
import online.bingzi.util.moneyPlayerPoints
import online.bingzi.util.moneyVault
import online.bingzi.view.View
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.warning
import taboolib.module.chat.colored
import taboolib.module.configuration.util.getStringColored
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.compat.replacePlaceholder

object UploadView : View {
    override fun open(sender: Player, username: String) {
        val get = OxygenRepository.get(username)
        if (get == null) {
            warning("出现错误！玩家在未生成仓库的时候尝试上传物品，已拦截操作。")
        } else {
            sender.openMenu<Linked<ItemStack>>("物品上传") {
                // 一共几行
                rows(6)
                // 可放置物品位置
                slots(
                    mutableListOf(
                        0, 1, 2, 3, 4, 5, 6, 7, 8,
                        9, 10, 11, 12, 13, 14, 15, 16, 17,
                        18, 19, 20, 21, 22, 23, 24, 25, 26,
                        27, 28, 29, 30, 31, 32, 33, 34, 35
                    )
                )
                // 元素
                elements {
                    val mutableList = mutableListOf<ItemStack>()
                    val inventory = sender.inventory
                    for (i in 0..inventory.size) {
                        val item = inventory.getItem(i)
                        if (item != null && item.type != Material.AIR) {
                            mutableList.add(item)
                        }
                    }
                    mutableList
                }
                // 展示的元素
                onGenerate { _, element, _, _ ->
                    element
                }
                // 返回主界面
                set(40, "Back".builderItem()) {
                    this.isCancelled = true
                    MainView.open(sender, username)
                }
                // 点击事件
                onClick { event, element ->
                    // 拦截玩家点击事件，以防玩家刷物品
                    event.isCancelled = true
                    // 防止玩家存进去NULL或者空气
                    if (element.type == Material.AIR) return@onClick
                    val player = event.clicker
                    if (get.count >= get.page) {
                        // 是不是有右键单击
                        val result = if (event.clickEvent().click.isRightClick) {
                            moneyPlayerPoints.remove(player, conf.getDouble("UploadItem.PlayerPoints"))
                        } else if (event.clickEvent().click.isLeftClick) {
                            moneyVault.remove(player, conf.getDouble("UploadItem.Vault"))
                        } else {
                            false
                        }
                        if (result) {
                            get.itemStacks.add(element)
                            event.inventory.setItem(event.rawSlot, ItemStack(Material.AIR))
                            player.inventory.removeItem(element)
                            OxygenRepository.set(get)
                        } else {
                            conf.getStringColored("Lang.UploadDeductionError")?.colored()?.replacePlaceholder(player)
                                ?.let { player.sendMessage(it) }
                        }
                    } else {
                        conf.getStringColored("Lang.UploadMax")?.colored()?.replacePlaceholder(player)?.let {
                            player.sendMessage(it)
                        }
                        return@onClick
                    }
                }
//                onClick { it ->
//
//                }
                onClose {
                    OxygenRepository.set(get)
                }
            }
        }

    }

}