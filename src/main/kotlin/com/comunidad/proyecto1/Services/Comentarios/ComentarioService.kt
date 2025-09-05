package com.comunidad.proyecto1.Services.Comentarios

import com.comunidad.proyecto1.Dtos.Comentarios.ComentarioDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.sql.Timestamp

@Service
class ComentarioService(private val jdbc: JdbcTemplate) {

    private val rowMapper = RowMapper { rs, _: Int ->
        ComentarioDto(
            id = rs.getLong("id"),
            aviso_id = rs.getLong("aviso_id"),
            usuario_id = rs.getLong("usuario_id"),
            contenido = rs.getString("contenido"),
            estado = rs.getInt("estado"),
            fecha = rs.getTimestamp("fecha")
        )
    }

    fun findAll(): List<ComentarioDto> {
        val sql = """
            SELECT id, aviso_id, usuario_id, contenido, estado, fecha
            FROM comentarios
            ORDER BY id ASC
        """.trimIndent()
        return jdbc.query(sql, rowMapper)
    }

    fun findById(id: Long): ComentarioDto? {
        val sql = """
            SELECT id, aviso_id, usuario_id, contenido, estado, fecha
            FROM comentarios
            WHERE id = ?
        """.trimIndent()
        return try { jdbc.queryForObject(sql, rowMapper, id) } catch (_: Exception) { null }
    }

    fun findByEstado(estado: Int): List<ComentarioDto> {
        val sql = """
            SELECT id, aviso_id, usuario_id, contenido, estado, fecha
            FROM comentarios
            WHERE estado = ?
            ORDER BY id ASC
        """.trimIndent()
        return jdbc.query(sql, rowMapper, estado)
    }
    fun findByDate(fecha: LocalDate): List<ComentarioDto> {
        val sql = """
        SELECT id, aviso_id, usuario_id, contenido, estado, fecha
        FROM comentarios
        WHERE fecha >= ? AND fecha < ?
        ORDER BY id ASC
    """.trimIndent()
        val inicio = Timestamp.valueOf(fecha.atStartOfDay())
        val fin = Timestamp.valueOf(fecha.plusDays(1).atStartOfDay())
        return jdbc.query(sql, rowMapper, inicio, fin)
    }

    fun findByDateRange(desde: LocalDate, hasta: LocalDate): List<ComentarioDto> {
        val sql = """
        SELECT id, aviso_id, usuario_id, contenido, estado, fecha
        FROM comentarios
        WHERE fecha >= ? AND fecha < ?
        ORDER BY id ASC
    """.trimIndent()
        val inicio = Timestamp.valueOf(desde.atStartOfDay())
        val fin = Timestamp.valueOf(hasta.plusDays(1).atStartOfDay())
        return jdbc.query(sql, rowMapper, inicio, fin)
    }

    fun create(dto: ComentarioDto): Int {
        val sql = """
            INSERT INTO comentarios (aviso_id, usuario_id, contenido, estado)
            VALUES (?, ?, ?, ?)
        """.trimIndent()
        return jdbc.update(sql, dto.getAvisoId(), dto.getUsuarioId(), dto.getContenido(), dto.getEstado())
    }

    fun update(id: Long, dto: ComentarioDto): Int {
        val sql = """
            UPDATE comentarios
            SET aviso_id = ?,
                usuario_id = ?,
                contenido = ?,
                estado = ?
            WHERE id = ?
        """.trimIndent()
        return jdbc.update(sql, dto.getAvisoId(), dto.getUsuarioId(), dto.getContenido(), dto.getEstado(), id)
    }

    fun delete(id: Long): Int {
        val sql = "DELETE FROM comentarios WHERE id = ?"
        return jdbc.update(sql, id)
    }
}
