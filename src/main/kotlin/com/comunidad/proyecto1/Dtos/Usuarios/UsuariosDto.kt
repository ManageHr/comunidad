package com.comunidad.proyecto1.Dtos.Usuarios

import java.time.LocalDate

class UsuariosDto (
    private var id:Long?=null,
    private var email:String?=null,
    private var nombre:String?=null,
    private var clave:String?=null,
    private var torre:String?=null,
    private var apartamento:String?=null,
    private var permiso_id:Long?=null,
    private var comunidad_id:Long?=null,
    private var estado:Int?=null,
    private var creado_en: LocalDate?=null
){
    fun getId():Long?{
        return id
    }
    fun setId(value:Long){
        id=value
    }

    fun getEmail(): String? = email
    fun setEmail(value: String) { email = value }
    fun getNombre(): String? = nombre
    fun setNombre(value: String) { nombre = value }
    fun getClave(): String? = clave
    fun setClave(value: String) { clave = value }

    fun getTorre(): String? = torre
    fun setTorre(value: String) { torre = value }

    fun getApartamento(): String? = apartamento
    fun setApartamento(value: String) { apartamento = value }

    fun getPermisoId(): Long? = permiso_id
    fun setPermisoId(value: Long) { permiso_id = value }

    fun getComunidadId(): Long? = comunidad_id
    fun setComunidadId(value: Long) { comunidad_id = value }

    fun getEstado(): Int? = estado
    fun setEstado(value: Int) { estado = value }

    fun getCreadoEn(): LocalDate? = creado_en
    fun setCreadoEn(value: LocalDate) { creado_en = value }
}