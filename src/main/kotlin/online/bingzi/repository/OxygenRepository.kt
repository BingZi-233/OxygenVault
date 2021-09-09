package online.bingzi.repository

import online.bingzi.bean.Oxygen
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Oxygen数据源
 */
abstract class OxygenRepository : CrudRepository<Oxygen, String> {
    /**
     * 获取有多少个玩家创建了仓库
     */
    abstract fun countByIdNotNull(): Long

    /**
     * 获取玩家仓库实体<br>
     * 如果玩家未创建isPresent()将返回false
     */
    abstract fun findByIdEquals(id: String): Optional<Oxygen>
}