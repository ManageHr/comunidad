package com.comunidad.proyecto1.Dtos.Permisos

class PermisosDto (
    private var id: Long,
    private var nombre: String,
    private var descripcion: String){
    fun getId():Long{ return id }
    fun setId(value:Long){ id=value }
    fun getNombre():String{ return nombre }
    fun setNombre(value:String){ nombre=value }
    fun getDescripcion():String{ return descripcion }
    fun setDescripcion(value:String){ nombre=descripcion}
}