package com.example.services

import com.example.models.DetallePackingList
import com.example.repositories.DetallePackingListRepository
import java.util.*

class DetallePackingListService(private val repository: DetallePackingListRepository) {
    fun createDetalle(
        folio: UUID,
        prendas: UUID,
        cantidad: Int
    ): DetallePackingList {
        val detalle = DetallePackingList(
            uuid = UUID.randomUUID(),
            folio = folio,
            prendas = prendas,
            cantidad = cantidad,
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )
        repository.create(detalle)
        return detalle
    }

    fun getDetalle(uuid: UUID): DetallePackingList? {
        return repository.getById(uuid)
    }

    fun updateDetalle(
        uuid: UUID,
        folio: UUID,
        prendas: UUID,
        cantidad: Int
    ): DetallePackingList? {
        val detalle = repository.getById(uuid) ?: return null
        val updatedDetalle = detalle.copy(
            folio = folio,
            prendas = prendas,
            cantidad = cantidad,
            fechaModificacion = System.currentTimeMillis()
        )
        return if (repository.update(updatedDetalle)) updatedDetalle else null
    }

    fun deleteDetalle(uuid: UUID): Boolean {
        return repository.delete(uuid)
    }

    fun listDetalles(): List<DetallePackingList> {
        return repository.getAll()
    }
}