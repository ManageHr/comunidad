package com.comunidad.proyecto1.Controllers.Permisos

import com.comunidad.proyecto1.Dtos.Permisos.PermisosDto
import com.comunidad.proyecto1.Services.Permisos.PermisosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.IncorrectResultSizeDataAccessException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/permisos")
class PermisosController {
    @Autowired
lateinit var permisosService: PermisosService
    @GetMapping
    fun obtenerTodos(): ResponseEntity<Any>{
        var filas =permisosService.getAll()
        return if (filas.isEmpty()) ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay registros que mostrar") else ResponseEntity.ok(filas)
    }
    @GetMapping("/{id}")
    fun obtenerId(@PathVariable id:Long): ResponseEntity<Any>{
        var consulta=permisosService.getById(id)
        return if (consulta==null){
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el id $id")
            }else{
                ResponseEntity.ok(consulta)
            }
    }
    @GetMapping("/ultimo")
    fun obtenerId(): ResponseEntity<Any>{
        var consulta=permisosService.getMaxId()
        return if (consulta==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existen datos")
        }else{
            ResponseEntity.ok(consulta)
        }
    }
    @PostMapping
    fun create(@RequestBody permisosDto: PermisosDto): ResponseEntity<Any>{
        val consulta=permisosService.create(permisosDto)
        return if (consulta>0){
            val ultimo= permisosService.getMaxId()
            ResponseEntity.ok("Creado con exito y con el id = $ultimo")
        }else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el permiso")
        }
    }
    @PutMapping("/{id}")
    fun actualizar(@PathVariable id:Long,@RequestBody permisosDto: PermisosDto): ResponseEntity<Any>{
        var consulta=permisosService.update(id,permisosDto)
        return if (consulta>0){
            var buscar=permisosService.getById(id)
            ResponseEntity.ok(buscar)
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al modificar el id $id")
        }

    }
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id:Long): String{
        var consulta=permisosService.delete(id)
        return if (consulta>0) {
            "Se elimino con exito el id $id"
        }else{
            "no se encontro el id $id"
        }
    }
    @GetMapping("/filtro/{nombre}")
    fun getFiltro(@PathVariable nombre:String): ResponseEntity<Any>{

        var resultado=permisosService.getFiltro(nombre)
        return if (!resultado.isEmpty()){
            ResponseEntity.ok(resultado)
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros")
        }
    }

}