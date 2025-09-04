package com.comunidad.proyecto1.Controllers.Comunidades

import com.comunidad.proyecto1.Dtos.Comunidades.ComunidadDto
import com.comunidad.proyecto1.Services.Comunidades.ComunidadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comunidades")
class ComunidadController {

    @Autowired
    lateinit var service: ComunidadService

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
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("mensaje" to "No se encontró la comunidad con id $id"))
    }

    @GetMapping(params = ["estado"])
    fun getByEstado(@RequestParam estado: Int): ResponseEntity<Any> {
        val data = service.findByEstado(estado)
        return if (data.isEmpty()) ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.ok(data)
    }

    @PostMapping
    fun create(@RequestBody dto: ComunidadDto): ResponseEntity<Any> {
        val rows = service.create(dto)
        return if (rows > 0) ResponseEntity.status(HttpStatus.CREATED).body(mapOf("mensaje" to "Comunidad creada correctamente", "filasAfectadas" to rows))
        else ResponseEntity.badRequest().body(mapOf("mensaje" to "No se pudo crear la comunidad"))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: ComunidadDto): ResponseEntity<Any> {
        val rows = service.update(id, dto)
        return if (rows > 0) ResponseEntity.ok(mapOf("mensaje" to "Comunidad actualizada correctamente", "filasAfectadas" to rows))
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("mensaje" to "No se encontró la comunidad con id $id"))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        val rows = service.delete(id)
        return if (rows > 0) ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("mensaje" to "No se encontró la comunidad con id $id"))
    }
}
