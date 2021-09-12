package online.bingzi.controller

import online.bingzi.util.Tools.conf
import online.bingzi.util.Tools.confView
import online.bingzi.view.viewimpl.MainView
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.platform.util.sendWarnMessage

/**
 * Oxygen命令拦截器
 */
@CommandHeader(name = "OxygenVault", aliases = ["OV", "ov", "Oxygen", "oxygen", "OXYGEN"])
object OxygenController {
    @CommandBody
    val open = mainCommand {
        dynamic(optional = false) {
            dynamic(optional = true) {
                suggestion<Player> { _, _ ->
                    val list = ArrayList<String>()
                    onlinePlayers().forEach {
                        list.add(it.name)
                    }
                    list.toList()
                }
                execute<Player> { sender, context, argument ->
                    if (sender.hasPermission("oxygenvault.op") || sender.name == argument) {
                        if (context.argument(-1) == "open") {
                            MainView.open(sender, argument)
                        }
                    } else {
                        sender.sendWarnMessage("您无权使用该条命令查看他人的仓库!")
                    }
                    return@execute
                }
            }
            suggestion<Player> { _, _ ->
                mutableListOf("open", "reload")
            }
            execute<Player> { sender, _, argument ->
                when (argument) {
                    "open" -> {
                        MainView.open(sender, sender.name)
                    }
                    "reload" -> {
                        conf.reload()
                        confView.reload()
                    }
                }
            }
        }
    }

}