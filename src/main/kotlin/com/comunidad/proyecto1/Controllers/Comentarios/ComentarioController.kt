package com.comunidad.proyecto1.Controllers.Comentarios

import com.comunidad.proyecto1.Dtos.Comentarios.ComentarioDto
import com.comunidad.proyecto1.Services.Comentarios.ComentarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comentarios")
class ComentarioController {

    @Autowired
    lateinit var service: ComentarioService

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val data = service.findAll()
        return if (data.isEmpty()) ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.ok(data)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Any> {
        val item = service.findById(id)
        return if (item != null) ResponseEntity.ok(item)
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("mensaje" to "No se encontró el comentario con id $id"))
    }

    @GetMapping(params = ["estado"])
    fun getByEstado(@RequestParam estado: Int): ResponseEntity<Any> {
        val data = service.findByEstado(estado)
        return if (data.isEmpty()) ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.ok(data)
    }

    @PostMapping
    fun create(@RequestBody dto: ComentarioDto): ResponseEntity<Any> {
        val rows = service.create(dto)
        return if (rows > 0) ResponseEntity.status(HttpStatus.CREATED).body(mapOf("mensaje" to "Comentario creado correctamente", "filasAfectadas" to rows))
        else ResponseEntity.badRequest().body(mapOf("mensaje" to "No se pudo crear el comentario"))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: ComentarioDto): ResponseEntity<Any> {
        val rows = service.update(id, dto)
        return if (rows > 0) {
            ResponseEntity.ok(
                mapOf(
                    "mensaje" to "Comentario actualizado correctamente",
                    "id" to id,
                    "filasAfectadas" to rows
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("mensaje" to "No se encontró el comentario con id $id"))
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        val rows = service.delete(id)
        return if (rows > 0) {
            ResponseEntity.ok(
                mapOf(
                    "mensaje" to "Comentario eliminado correctamente",
                    "id" to id,
                    "filasAfectadas" to rows
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("mensaje" to "No se encontró el comentario con id $id"))
        }
    }

}
