package com.comunidad.proyecto1.Services.Notificaciones

import com.comunidad.proyecto1.Dtos.Notficaciones.NotificacionesDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class NotificacionesService(private val  jdbc : JdbcTemplate) {

    private val rowMapper = RowMapper { rs, _: Int ->
        NotificacionesDto(
            id = rs.getInt("id"),
            usuario_id = rs.getLong("usuario_id"),
            aviso_id = rs.getLong("aviso_id"),
            tipo = rs.getString("tipo"),
            mensaje = rs.getString("mensaje"),
            leida = rs.getInt("leida"),
            fecha_creacion = rs.getTimestamp("fecha_creacion")
        )
    }

    fun findAll () : List<NotificacionesDto> {
        val sql  = """
            SELECT *
            FROM `notificaciones`
            ORDER BY id ASC
        """.trimIndent()
        return jdbc.query(sql, rowMapper)
    }
    fun findById(id : Int) : NotificacionesDto? {
        val sql = """
            SELECT *
            FROM `notificaciones`
            WHERE id = ?
        """.trimIndent()
        return try {
            jdbc.queryForObject(sql, rowMapper, id)
        } catch (_: Exception) {
            null
        }
    }
    fun findByStatus(status : Int) : List<NotificacionesDto> {
        val sql = """
                SELECT *
                FROM `notificaciones`
                WHERE leida = ?
            """.trimIndent()
        return jdbc.query(sql,rowMapper,status)
    }

    fun create(dto: NotificacionesDto): Int {
        val sql = """
            INSERT INTO `notificaciones`
                (usuario_id, aviso_id, tipo, mensaje, leida)
            VALUES (?, ?, ?, ?, ?)
        """.trimIndent()
        return jdbc.update(
            sql,
            dto.getUsuarioId(),
            dto.getAvisoId(),
            dto.getTipo(),
            dto.getMensaje(),
            dto.getLeida(),
        )
    }

    fun update(id: Int, dto: NotificacionesDto): Int {
        val sql = """
            UPDATE `notificaciones`
            SET usuario_id = ?,
                aviso_id   = ?,
                tipo       = ?,
                mensaje    = ?,
                leida      = ?,
                fecha_creacion = ?
            WHERE id = ?
        """.trimIndent()

        return jdbc.update(
            sql,
            dto.getUsuarioId(),
            dto.getAvisoId(),
            dto.getTipo(),
            dto.getMensaje(),
            dto.getLeida(),
            Timestamp(dto.getFechaCreacion().time),
            id
        )
    }

    fun delete(id: Int): Int {
        val sql = "DELETE FROM `notificaciones` WHERE id = ?"
        return jdbc.update(sql, id)
    }

}