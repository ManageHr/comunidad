package com.comunidad.proyecto1.Dtos.Categorias

class CategoriaDto(
    private var id: Long,
    private var nombre: String,
    private var descripcion: String?
) {
    fun getId() = id
    fun setId(nuevoId: Long) { id = nuevoId }

    fun getNombre() = nombre
    fun setNombre(nuevoNombre: String) { nombre = nuevoNombre }

    fun getDescripcion() = descripcion
    fun setDescripcion(nuevaDescripcion: String?) { descripcion = nuevaDescripcion }
}
