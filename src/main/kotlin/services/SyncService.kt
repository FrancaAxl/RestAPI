package com.example.services

import com.example.models.*
import com.example.repositories.SyncRepository
import java.util.*

class SyncService(private val syncRepository: SyncRepository) {

    fun getPendingSyncData(): SyncPayload {
        return SyncPayload(
            prendas = syncRepository.getUnsyncedPrendas(),
            usuarios = syncRepository.getUnsyncedUsuarios()
        )
    }

    fun confirmSync(syncedItems: List<SyncedItem>) {
        syncedItems.forEach { item ->
            syncRepository.markAsSynced(
                UUID.fromString(item.uuid),
                item.tableName
            )
        }
    }
}

data class SyncPayload(
    val prendas: List<Prenda>,
    val usuarios: List<Usuario>
)

data class SyncedItem(
    val uuid: String,
    val tableName: String
)