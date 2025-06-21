package com.example.services

import com.example.models.Cargo
import com.example.repositories.CargoRepository
import java.util.*

class CargoService(private val repository: CargoRepository) {
    fun createCargo(nombreCargo: String, nivelCargo: Int): Cargo {
        val cargo = Cargo(
            uuid = UUID.randomUUID(),
            nombreCargo = nombreCargo,
            nivelCargo = nivelCargo,
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )
        repository.create(cargo)
        return cargo
    }

    fun getCargo(uuid: UUID): Cargo? {
        return repository.getById(uuid)
    }

    fun updateCargo(uuid: UUID, nombreCargo: String, nivelCargo: Int): Cargo? {
        val cargo = repository.getById(uuid) ?: return null
        val updatedCargo = cargo.copy(
            nombreCargo = nombreCargo,
            nivelCargo = nivelCargo,
            fechaModificacion = System.currentTimeMillis()
        )
        return if (repository.update(updatedCargo)) updatedCargo else null
    }

    fun deleteCargo(uuid: UUID): Boolean {
        return repository.delete(uuid)
    }

    fun listCargos(): List<Cargo> {
        return repository.getAll()
    }
}