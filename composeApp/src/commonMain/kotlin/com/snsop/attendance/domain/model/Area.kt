package com.snsop.attendance.domain.model

import com.snsop.attendance.domain.model.ui.SpinnerItem


sealed class Area(
    open var id: Int = -1,
    open var parentId: Int = -1,
    override var name: String = ""
) : SpinnerItem() {

    data class State(
        override var id: Int = -1,
        override var parentId: Int = -1,
        override var name: String = ""
    ) : Area(id = id, parentId = parentId, name = name)

    data class County(
        override var id: Int = -1,
        override var parentId: Int = -1,
        override var name: String = ""
    ) : Area(id = id, parentId = parentId, name = name)

    data class Payam(
        override var id: Int = -1,
        override var parentId: Int = -1,
        override var name: String = ""
    ) : Area(id = id, parentId = parentId, name = name)

    data class Boma(
        override var id: Int = -1,
        override var parentId: Int = -1,
        override var name: String = ""
    ) : Area(id = id, parentId = parentId, name = name)

    fun type(): String {
        return when (this) {
            is State -> "State"
            is County -> "County"
            is Payam -> "Payam"
            is Boma -> "Boma"
        }
    }
}
