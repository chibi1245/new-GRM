package com.snsop.attendance.domain.network

import com.snsop.attendance.domain.network.ConnectionListener.Companion.instance
import com.snsop.attendance.utils.log
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.ConnectivityOptions
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class ConnectionListener() {
    private val connectivity = Connectivity(options = ConnectivityOptions(true))

    val connectionStatusFlow = connectivity.statusUpdates.onEach {
        it.log(TAG)
    }.stateIn(
        MainScope(),
        initialValue = Connectivity.Status.Disconnected,
        started = SharingStarted.Eagerly
    )

    val isConnected
        get() = connectionStatusFlow.value.isConnected

    init {
        instance = this
    }

    fun unregisterListener() {
        connectivity.stop()
    }

    companion object {
        const val TAG = "ConnectionListener"
        var instance: ConnectionListener? = null
    }
}

val connectionListener by lazy { instance ?: ConnectionListener() }
