package online.bingzi

import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile

class OxygenVault : Plugin() {
    // 配置文件注册
    @Config
    lateinit var config: SecuredFile
        private set

    override fun onLoad() {

    }

    override fun onEnable() {

    }

    override fun onDisable() {

    }
}