package com.comunidad.proyecto1.Controllers.Avisos

import com.comunidad.proyecto1.Dtos.Avisos.AvisosDto
import com.comunidad.proyecto1.Services.Avisos.AvisosService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avisos")
class AvisosController(private val avisosService: AvisosService) {

    @GetMapping
    fun listar() = avisosService.listarTodos()

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long) =
        avisosService.obtenerPorId(id) ?: mapOf("mensaje" to "No existe aviso con id $id")

    @GetMapping("/titulo/{titulo}")
    fun obtenerPorTitulo(@PathVariable titulo: String) =
        avisosService.obtenerPorTitulo(titulo)

    @PostMapping
    fun crear(@RequestBody dto: AvisosDto) =
        if (avisosService.crear(dto) > 0) mapOf("mensaje" to "Aviso creado")
        else mapOf("mensaje" to "Error al crear")

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody dto: AvisosDto) =
        if (avisosService.actualizar(id, dto) > 0) mapOf("mensaje" to "Aviso actualizado")
        else mapOf("mensaje" to "No se actualizó")

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long) =
        if (avisosService.eliminar(id) > 0) mapOf("mensaje" to "Aviso eliminado")
        else mapOf("mensaje" to "No se eliminó")

    @PatchMapping("/{id}/estado")
    fun cambiarEstado(@PathVariable id: Long, @RequestBody body: Map<String, Int>) =
        if (avisosService.actualizarSoloEstado(id, body["estado"] ?: 0) > 0)
            mapOf("mensaje" to "Estado actualizado")
        else mapOf("mensaje" to "No se actualizó estado")

    @PatchMapping("/{id}/urgente")
    fun cambiarUrgente(@PathVariable id: Long, @RequestBody body: Map<String, Int>) =
        if (avisosService.actualizarSoloUrgente(id, body["urgente"] ?: 0) > 0)
            mapOf("mensaje" to "Urgente actualizado")
        else mapOf("mensaje" to "No se actualizó urgente")
}
