package com.snsop.attendance.domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class SyncStatus {
    Saved,
    Synced,
    Failed;

    fun isSynced(): Boolean {
        return this == Synced
    }
}