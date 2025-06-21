package com.example.services

import com.example.models.PackingList
import com.example.repositories.PackingListRepository
import java.util.*

class PackingListService(private val repository: PackingListRepository) {
    fun createPackingList(
        folio: String,
        envia: String,
        recibe: String,
        transporte: String,
        codigoContenedor: String
    ): PackingList {
        val packingList = PackingList(
            uuid = UUID.randomUUID(),
            folio = folio,
            envia = envia,
            recibe = recibe,
            transporte = transporte,
            codigoContenedor = codigoContenedor,
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )
        repository.create(packingList)
        return packingList
    }

    fun getPackingList(uuid: UUID): PackingList? {
        return repository.getById(uuid)
    }

    fun updatePackingList(
        uuid: UUID,
        folio: String,
        envia: String,
        recibe: String,
        transporte: String,
        codigoContenedor: String
    ): PackingList? {
        val packingList = repository.getById(uuid) ?: return null
        val updatedPackingList = packingList.copy(
            folio = folio,
            envia = envia,
            recibe = recibe,
            transporte = transporte,
            codigoContenedor = codigoContenedor,
            fechaModificacion = System.currentTimeMillis()
        )
        return if (repository.update(updatedPackingList)) updatedPackingList else null
    }

    fun deletePackingList(uuid: UUID): Boolean {
        return repository.delete(uuid)
    }

    fun listPackingLists(): List<PackingList> {
        return repository.getAll()
    }
}