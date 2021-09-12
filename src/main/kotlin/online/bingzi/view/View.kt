package online.bingzi.view

import org.bukkit.entity.Player

interface View {
    fun open(sender: Player, username: String)
}