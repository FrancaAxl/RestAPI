package com.example.services

import com.example.models.DetalleInventarioAlmacen
import com.example.repositories.DetalleInventarioAlmacenRepository
import java.util.*

class DetalleInventarioAlmacenService(private val repository: DetalleInventarioAlmacenRepository) {
    fun createDetalle(
        almacen: UUID,
        prendas: UUID,
        cantidad: Int
    ): DetalleInventarioAlmacen {
        val detalle = DetalleInventarioAlmacen(
            uuid = UUID.randomUUID(),
            almacen = almacen,
            prendas = prendas,
            cantidad = cantidad,
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )
        repository.create(detalle)
        return detalle
    }

    fun getDetalle(uuid: UUID): DetalleInventarioAlmacen? {
        return repository.getById(uuid)
    }

    fun updateDetalle(
        uuid: UUID,
        almacen: UUID,
        prendas: UUID,
        cantidad: Int
    ): DetalleInventarioAlmacen? {
        val detalle = repository.getById(uuid) ?: return null
        val updatedDetalle = detalle.copy(
            almacen = almacen,
            prendas = prendas,
            cantidad = cantidad,
            fechaModificacion = System.currentTimeMillis()
        )
        return if (repository.update(updatedDetalle)) updatedDetalle else null
    }

    fun deleteDetalle(uuid: UUID): Boolean {
        return repository.delete(uuid)
    }

    fun listDetalles(): List<DetalleInventarioAlmacen> {
        return repository.getAll()
    }
}