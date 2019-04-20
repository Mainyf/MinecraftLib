package io.github.mainyf.minecraftlib.data

import java.util.*

open class BaseData {
    var uid: Long? = null
    var uuid: UUID? = null
    var createTime: Date? = null

    fun copyDataTo(otherData: BaseData): BaseData {
        otherData.uid = this.uid
        otherData.uuid = this.uuid
        otherData.createTime = this.createTime
        return otherData
    }

}