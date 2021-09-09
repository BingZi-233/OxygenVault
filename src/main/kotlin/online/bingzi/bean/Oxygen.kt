package online.bingzi.bean

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Oxygen Bean
 */
@Entity
class Oxygen {
    @Id
    var id: String = ""
    var max: Int = -1
    var itemStackList: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Oxygen

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}