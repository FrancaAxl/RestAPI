package com.example.services

import com.example.models.Prenda
import com.example.repositories.PrendaRepository
import java.util.*

class PrendaService(private val repository: PrendaRepository) {
    fun createPrenda(
        codigoBarras: String,
        nombreProducto: String,
        talla: String,
        color: String
    ): Prenda {
        val prenda = Prenda(
            uuid = UUID.randomUUID(),
            codigoBarras = codigoBarras,
            nombreProducto = nombreProducto,
            talla = talla,
            color = color,
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )
        repository.create(prenda)
        return prenda
    }

    fun getPrenda(uuid: UUID): Prenda? {
        return repository.getById(uuid)
    }

    fun getPrendaByCodigo(codigo: String): Prenda? {
        return repository.getByCodigoBarras(codigo)
    }

    fun updatePrenda(
        uuid: UUID,
        codigoBarras: String,
        nombreProducto: String,
        talla: String,
        color: String
    ): Prenda? {
        val prenda = repository.getById(uuid) ?: return null
        val updatedPrenda = prenda.copy(
            codigoBarras = codigoBarras,
            nombreProducto = nombreProducto,
            talla = talla,
            color = color,
            fechaModificacion = System.currentTimeMillis()
        )
        return if (repository.update(updatedPrenda)) updatedPrenda else null
    }

    fun deletePrenda(uuid: UUID): Boolean {
        return repository.delete(uuid)
    }

    fun listPrendas(): List<Prenda> {
        return repository.getAll()
    }

    fun getPrendasNoSincronizadas(): List<Prenda> {
        return repository.getUnsynced()
    }
}