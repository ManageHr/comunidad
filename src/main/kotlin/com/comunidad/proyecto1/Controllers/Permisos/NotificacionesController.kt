package com.comunidad.proyecto1.Controllers.Permisos

import com.comunidad.proyecto1.Dtos.Permisos.NotificacionesDto
import com.comunidad.proyecto1.Services.Permisos.NotificacionesService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notificaciones")
class NotificacionesController {
    @Autowired
    lateinit var service: NotificacionesService

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val result = service.findAll()
        return if (result != null) {
            ResponseEntity.ok(result)
        } else {
            ResponseEntity
                .status(404)
                .body(mapOf("mensaje" to "No se encontraron usuarios"))
        }
    }

    @GetMapping("/{id}")
    fun getByID(@PathVariable id: Int): ResponseEntity<Any> {
        val notificacion = service.findById(id)
        return if (notificacion != null) {
            ResponseEntity.ok(notificacion)
        } else {
            ResponseEntity
                .status(400)
                .body(mapOf("mensaje" to "No se encontraron la notificacion con id $id"))
        }
    }

    @GetMapping(params = ["leida"])
    fun getByLeida(@RequestParam leida: Int): ResponseEntity<Any> {
        if (leida != 1 && leida != 2) {
            return ResponseEntity.badRequest()
                .body(mapOf("mensaje" to "El parámetro 'leida' debe ser 1 (no leída) o 2 (leída)"))
        }
        val data = service.findByStatus(leida)
        return if (data.isEmpty()) ResponseEntity.noContent().build()
        else ResponseEntity.ok(data)
    }

    @PostMapping
    fun create(@RequestBody dto: NotificacionesDto): ResponseEntity<Any> {
        val rows = service.create(dto)
        return if (rows > 0) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(mapOf("mensaje" to "Notificación creada correctamente", "filasAfectadas" to rows))
        } else {
            ResponseEntity.badRequest()
                .body(mapOf("mensaje" to "No se pudo crear la notificación"))
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody dto: NotificacionesDto): ResponseEntity<Any> {
        val rows = service.update(id, dto)
        return if (rows > 0) {
            ResponseEntity.ok(mapOf("mensaje" to "Notificación actualizada correctamente", "filasAfectadas" to rows))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("mensaje" to "No se encontró la notificación con id $id"))
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        val rows = service.delete(id)
        return if (rows > 0) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("mensaje" to "No se encontró la notificación con id $id"))
        }
    }

}