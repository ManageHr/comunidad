package com.comunidad.proyecto1.Dtos.Comentarios

import java.sql.Timestamp

class ComentarioDto(
    private var id: Long? = null,
    private var aviso_id: Long,
    private var usuario_id: Long,
    private var contenido: String,
    private var estado: Int = 1,
    private var fecha: Timestamp? = null
) {
    fun getId(): Long? = id
    fun setId(id: Long?) { this.id = id }

    fun getAvisoId(): Long = aviso_id
    fun setAvisoId(aviso_id: Long) { this.aviso_id = aviso_id }

    fun getUsuarioId(): Long = usuario_id
    fun setUsuarioId(usuario_id: Long) { this.usuario_id = usuario_id }

    fun getContenido(): String = contenido
    fun setContenido(contenido: String) { this.contenido = contenido }

    fun getEstado(): Int = estado
    fun setEstado(estado: Int) { this.estado = estado }

    fun getFecha(): Timestamp? = fecha
    fun setFecha(fecha: Timestamp?) { this.fecha = fecha }
}
