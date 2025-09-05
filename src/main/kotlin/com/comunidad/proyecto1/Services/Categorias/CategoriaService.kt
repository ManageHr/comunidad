package com.comunidad.proyecto1.Services.Categorias

import com.comunidad.proyecto1.Dtos.Categorias.CategoriaDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class CategoriaService(private val jdbcTemplate: JdbcTemplate) {

    private val categoriaMapper = RowMapper<CategoriaDto> { resultSet, _ ->
        CategoriaDto(
            resultSet.getLong("id"),
            resultSet.getString("nombre"),
            resultSet.getString("descripcion")
        )
    }

    fun listarCategorias(): List<CategoriaDto> =
        jdbcTemplate.query(
            "SELECT id, nombre, descripcion FROM categorias_avisos ORDER BY id DESC",
            categoriaMapper
        )

    fun buscarCategoriaPorId(categoriaId: Long): CategoriaDto? =
        jdbcTemplate.query(
            "SELECT id, nombre, descripcion FROM categorias_avisos WHERE id = ?",
            categoriaMapper,
            categoriaId
        ).firstOrNull()

    fun buscarCategoriasPorNombre(nombreCategoria: String): List<CategoriaDto> =
        jdbcTemplate.query(
            "SELECT id, nombre, descripcion FROM categorias_avisos WHERE nombre LIKE ?",
            categoriaMapper,
            "%$nombreCategoria%"
        )

    fun crearCategoria(categoriaDto: CategoriaDto): Int =
        jdbcTemplate.update(
            "INSERT INTO categorias_avisos (nombre, descripcion) VALUES (?, ?)",
            categoriaDto.getNombre(),
            categoriaDto.getDescripcion()
        )

    fun actualizarCategoria(categoriaId: Long, categoriaDto: CategoriaDto): Int =
        jdbcTemplate.update(
            "UPDATE categorias_avisos SET nombre = ?, descripcion = ? WHERE id = ?",
            categoriaDto.getNombre(),
            categoriaDto.getDescripcion(),
            categoriaId
        )

    fun eliminarCategoria(categoriaId: Long): Int =
        jdbcTemplate.update(
            "DELETE FROM categorias_avisos WHERE id = ?",
            categoriaId
        )
}
