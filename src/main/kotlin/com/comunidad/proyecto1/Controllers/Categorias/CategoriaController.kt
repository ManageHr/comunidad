package com.comunidad.proyecto1.Controllers.Categorias

import com.comunidad.proyecto1.Dtos.Categorias.CategoriaDto
import com.comunidad.proyecto1.Services.Categorias.CategoriaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categorias")
class CategoriaController(private val categoriaService: CategoriaService) {

    @GetMapping
    fun listarCategorias() = categoriaService.listarCategorias()

    @GetMapping("/{id}")
    fun obtenerCategoriaPorId(@PathVariable id: Long) =
        categoriaService.buscarCategoriaPorId(id)
            ?: mapOf("mensaje" to "No existe categoría con id $id")

    @GetMapping("/buscar")
    fun buscarCategoriasPorNombre(@RequestParam nombre: String) =
        categoriaService.buscarCategoriasPorNombre(nombre)

    @PostMapping
    fun crearCategoria(@RequestBody categoriaDto: CategoriaDto) =
        if (categoriaService.crearCategoria(categoriaDto) > 0)
            mapOf("mensaje" to "Categoría creada")
        else
            mapOf("mensaje" to "Error al crear la categoría")

    @PutMapping("/{id}")
    fun actualizarCategoria(@PathVariable id: Long, @RequestBody categoriaDto: CategoriaDto) =
        if (categoriaService.actualizarCategoria(id, categoriaDto) > 0)
            mapOf("mensaje" to "Categoría actualizada")
        else
            mapOf("mensaje" to "No se actualizó la categoría")

    @DeleteMapping("/{id}")
    fun eliminarCategoria(@PathVariable id: Long) =
        if (categoriaService.eliminarCategoria(id) > 0)
            mapOf("mensaje" to "Categoría eliminada")
        else
            mapOf("mensaje" to "No se eliminó la categoría")
}

