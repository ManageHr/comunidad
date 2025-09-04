package com.comunidad.proyecto1.Dtos.Permisos

import java.math.BigInteger
import java.sql.Date
import java.sql.Timestamp

class NotificacionesDto(
    private var id: Int,
    private var usuario_id : Long,
    private var aviso_id : Long,
    private var tipo : String,
    private var mensaje: String,
    private var leida : Int,
    private var fecha_creacion: Timestamp = Timestamp(System.currentTimeMillis())

) {
    fun getId(): Int {
        return id
    }
    fun setId(id: Int) {
        this.id = id
    }
    fun getUsuarioId(): Long {
        return usuario_id
    }
    fun setUsuarioId(usuario_id: Long) {
        this.usuario_id = usuario_id
    }
    fun getAvisoId(): Long {
        return aviso_id
    }
    fun setAvisoId(aviso_id: Long) {
        this.aviso_id = aviso_id
    }
    fun getTipo(): String {
        return tipo
    }
    fun setTipo(tipo: String) {
        this.tipo = tipo
    }
    fun getMensaje(): String {
        return mensaje
    }
    fun setMensaje(mensaje: String) {
        this.mensaje = mensaje
    }
    fun getLeida(): Int {
        return leida
    }
    fun setLeida(leida: Int) {
        this.leida = leida
    }
    fun getFechaCreacion(): Timestamp {
        return fecha_creacion
    }
    fun setFechaCreacion(fecha_creacion: Timestamp) {
        this.fecha_creacion = fecha_creacion
    }
}