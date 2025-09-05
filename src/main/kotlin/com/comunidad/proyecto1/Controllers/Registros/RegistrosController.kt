package com.comunidad.proyecto1.Controllers.Registros

import com.comunidad.proyecto1.Dtos.Registros.RegistrosDto
import com.comunidad.proyecto1.Dtos.Usuarios.UsuariosDto
import com.comunidad.proyecto1.Services.Registros.RegistrosService
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
@RequestMapping("/api/registros")
class RegistrosController {
    @Autowired
    lateinit var registrosService: RegistrosService
    @GetMapping
    fun getAll(): ResponseEntity<Any>{
        var consulta=registrosService.getALl()
        return if (consulta.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen datos")
        }else {
            ResponseEntity.ok(consulta)
        }

    }

    @GetMapping("/usuario/{id}")
    fun obtenerPorEstado(@PathVariable id:Int): ResponseEntity<Any>{
        var consulta=registrosService.getUsuario(id)
        return if (consulta==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existen estados con el id $id")
        }else{
            ResponseEntity.ok(consulta)
        }
    }



}