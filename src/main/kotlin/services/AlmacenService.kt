package com.example.services

import com.example.models.Almacen
import com.example.repositories.AlmacenRepository
import java.util.*

class AlmacenService(private val repository: AlmacenRepository) {
    fun createAlmacen(nombre: String, personal: UUID): Almacen {
        val almacen = Almacen(
            uuid = UUID.randomUUID(),
            nombre = nombre,
            personal = personal,
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )
        repository.create(almacen)
        return almacen
    }

    fun getAlmacen(uuid: UUID): Almacen? {
        return repository.getById(uuid)
    }

    fun updateAlmacen(uuid: UUID, nombre: String, personal: UUID): Almacen? {
        val almacen = repository.getById(uuid) ?: return null
        val updatedAlmacen = almacen.copy(
            nombre = nombre,
            personal = personal,
            fechaModificacion = System.currentTimeMillis()
        )
        return if (repository.update(updatedAlmacen)) updatedAlmacen else null
    }

    fun deleteAlmacen(uuid: UUID): Boolean {
        return repository.delete(uuid)
    }

    fun listAlmacenes(): List<Almacen> {
        return repository.getAll()
    }
}