package com.comunidad.proyecto1.Dtos.Registros

import java.time.LocalDate

class RegistrosDto(private var id: Long?=null, private var usuario:Long,private var fecha: LocalDate) {
    fun getId():Long?{ return id }
    fun setId(value:Long){id=value}
    fun getUsuario():Long?{ return usuario }
    fun setUsuario(value:Long){usuario=value}
    fun getFecha():LocalDate?{ return fecha }
    fun setFecha(value: LocalDate){fecha=value}
}