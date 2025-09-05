package com.comunidad.proyecto1.Services.Avisos

import com.comunidad.proyecto1.Dtos.Avisos.AvisosDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.LocalDate

@Service
class AvisosService(private val jdbcTemplate: JdbcTemplate) {

    private val row = RowMapper { rs: ResultSet, _: Int ->
        AvisosDto(
            id = rs.getLong("id"),
            usuarioId = rs.getLong("usuario_id"),
            comunidadId = rs.getLong("comunidad_id"),
            categoriaId = rs.getLong("categoria_id"),
            titulo = rs.getString("titulo"),
            descripcion = rs.getString("descripcion"),
            fechaPublicacion = rs.getDate("fecha_publicacion").toLocalDate(),
            fechaLimite = rs.getDate("fecha_limite")?.toLocalDate(),
            ubicacion = rs.getString("ubicacion"),
            estado = rs.getInt("estado"),
            urgente = rs.getInt("urgente")
        )
    }

    fun listarTodos(): List<AvisosDto> =
        jdbcTemplate.query("SELECT * FROM avisos ORDER BY id DESC", row)

    fun obtenerPorId(id: Long): AvisosDto? =
        jdbcTemplate.query("SELECT * FROM avisos WHERE id = ?", row, id).firstOrNull()

    fun obtenerPorTitulo(titulo: String): List<AvisosDto> =
        jdbcTemplate.query("SELECT * FROM avisos WHERE titulo LIKE ?", row, "%$titulo%")

    fun crear(dto: AvisosDto): Int {
        val sql = """
            INSERT INTO avisos (usuario_id, comunidad_id, categoria_id, titulo, descripcion,
                                fecha_publicacion, fecha_limite, ubicacion, estado, urgente)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()
        return jdbcTemplate.update(
            sql,
            dto.usuarioId,
            dto.comunidadId,
            dto.categoriaId,
            dto.titulo,
            dto.descripcion,
            dto.fechaPublicacion,
            dto.fechaLimite,
            dto.ubicacion,
            dto.estado,
            dto.urgente
        )
    }

    fun actualizar(id: Long, dto: AvisosDto): Int {
        val sql = """
            UPDATE avisos
            SET usuario_id=?, comunidad_id=?, categoria_id=?, titulo=?, descripcion=?,
                fecha_publicacion=?, fecha_limite=?, ubicacion=?, estado=?, urgente=?
            WHERE id=?
        """.trimIndent()
        return jdbcTemplate.update(
            sql,
            dto.usuarioId,
            dto.comunidadId,
            dto.categoriaId,
            dto.titulo,
            dto.descripcion,
            dto.fechaPublicacion,
            dto.fechaLimite,
            dto.ubicacion,
            dto.estado,
            dto.urgente,
            id
        )
    }

    fun eliminar(id: Long): Int =
        jdbcTemplate.update("DELETE FROM avisos WHERE id=?", id)

    fun actualizarSoloEstado(id: Long, estado: Int): Int =
        jdbcTemplate.update("UPDATE avisos SET estado=? WHERE id=?", estado, id)

    fun actualizarSoloUrgente(id: Long, urgente: Int): Int =
        jdbcTemplate.update("UPDATE avisos SET urgente=? WHERE id=?", urgente, id)
}
