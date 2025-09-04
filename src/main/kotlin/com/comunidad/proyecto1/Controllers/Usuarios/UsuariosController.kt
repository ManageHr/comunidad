package com.comunidad.proyecto1.Controllers.Usuarios

import com.comunidad.proyecto1.Dtos.Usuarios.UsuariosDto
import com.comunidad.proyecto1.Services.Usuarios.UsuariosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/usuarios")
class UsuariosController {
    @Autowired
    lateinit var usuariosService: UsuariosService
    @GetMapping
    fun getAll(): ResponseEntity<Any>{
        var consulta=usuariosService.getALl()
        return if (consulta.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen datos")
        }else {
            ResponseEntity.ok(consulta)
        }

    }
    @GetMapping("/{id}")
    fun obtenerId(@PathVariable id:Long): ResponseEntity<Any>{
        var consulta=usuariosService.getById(id)
        return if (consulta==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el id $id")
        }else{
            ResponseEntity.ok(consulta)
        }
    }
    @GetMapping("/estado/{id}")
    fun obtenerPorEstado(@PathVariable id:Int): ResponseEntity<Any>{
        var consulta=usuariosService.getEstados(id)
        return if (consulta==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existen estados con el id $id")
        }else{
            ResponseEntity.ok(consulta)
        }
    }
    @GetMapping("/permisos/{id}")
    fun obtenerId(@PathVariable id:Int): ResponseEntity<Any>{
        var consulta=usuariosService.getPermisos(id)
        return if (consulta==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe permisos con el id $id")
        }else{
            ResponseEntity.ok(consulta)
        }
    }
    @PostMapping("/login")
    fun login(@RequestBody usuariosDto: UsuariosDto): ResponseEntity<Any> {
        val usuario = usuariosService.login(
            usuariosDto.getEmail()?:"",
            usuariosDto.getClave()?:""
        )

        return if (usuario != null) {
            ResponseEntity.ok("Usuario logeado con exito")

        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas")
        }
    }
    @GetMapping("/ultimo")
    fun obtenerId(): ResponseEntity<Any>{
        var consulta=usuariosService.getMaxId()
        return if (consulta==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existen datos")
        }else{
            ResponseEntity.ok(consulta)
        }
    }
    @PostMapping
    fun create(@RequestBody usuariosDto: UsuariosDto): ResponseEntity<Any>{
        val consulta=usuariosService.create(usuariosDto)
        return if (consulta>0){
            val ultimo= usuariosService.getMaxId()
            ResponseEntity.ok("Creado con exito y con el id = $ultimo")
        }else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el permiso")
        }
    }
    @PutMapping("/{id}")
    fun actualizar(@PathVariable id:Long,@RequestBody usuariosDto: UsuariosDto): ResponseEntity<Any>{
        var consulta=usuariosService.update(id,usuariosDto)
        return if (consulta>0){
            var buscar=usuariosService.getById(id)
            ResponseEntity.ok(buscar)
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al modificar el id $id")
        }

    }
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id:Long): String{
        var consulta=usuariosService.delete(id)
        return if (consulta>0) {
            "Se elimino con exito el id $id"
        }else{
            "no se encontro el id $id"
        }
    }
    @GetMapping("/filtro/{nombre}")
    fun getFiltro(@PathVariable nombre:String): ResponseEntity<Any>{

        var resultado=usuariosService.getFiltro(nombre)
        return if (!resultado.isEmpty()){
            ResponseEntity.ok(resultado)
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros")
        }
    }
    @PutMapping("/estado/{id}")
    fun actualizarEstado(@PathVariable id:Long,@RequestBody usuariosDto: UsuariosDto): ResponseEntity<Any>{

        var resultado=usuariosService.updateEstado(id,usuariosDto)
        return if (resultado>0){
            ResponseEntity.ok("Se actualizo con exito el estado")
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros")
        }
    }
    @PutMapping("/permiso/{id}")
    fun actualizarPermiso(@PathVariable id:Long,@RequestBody usuariosDto: UsuariosDto): ResponseEntity<Any>{

        var resultado=usuariosService.updatePermiso(id,usuariosDto)
        return if (resultado>0){
            ResponseEntity.ok("Se actualizo con exito el permiso")
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros")
        }
    }
}