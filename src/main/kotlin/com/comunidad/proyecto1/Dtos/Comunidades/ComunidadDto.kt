package com.comunidad.proyecto1.Dtos.Comunidades

import java.sql.Timestamp

class ComunidadDto(
    private var id: Long? = null,
    private var nombre: String,
    private var direccion: String,
    private var codigo_acceso: String,
    private var estado: Int = 1,
    private var creado_en: Timestamp? = null
) {
    fun getId(): Long? = id
    fun setId(id: Long?) { this.id = id }

    fun getNombre(): String = nombre
    fun setNombre(nombre: String) { this.nombre = nombre }

    fun getDireccion(): String = direccion
    fun setDireccion(direccion: String) { this.direccion = direccion }

    fun getCodigoAcceso(): String = codigo_acceso
    fun setCodigoAcceso(codigo_acceso: String) { this.codigo_acceso = codigo_acceso }

    fun getEstado(): Int = estado
    fun setEstado(estado: Int) { this.estado = estado }

    fun getCreadoEn(): Timestamp? = creado_en
    fun setCreadoEn(creado_en: Timestamp?) { this.creado_en = creado_en }
}
