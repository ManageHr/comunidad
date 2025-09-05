package com.comunidad.proyecto1.Services.Comunidades

import com.comunidad.proyecto1.Dtos.Comunidades.ComunidadDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class ComunidadService(private val jdbc: JdbcTemplate) {

    private val rowMapper = RowMapper { rs, _: Int ->
        ComunidadDto(
            id = rs.getLong("id"),
            nombre = rs.getString("nombre"),
            direccion = rs.getString("direccion"),
            codigo_acceso = rs.getString("codigo_acceso"),
            estado = rs.getInt("estado"),
            creado_en = rs.getTimestamp("creado_en")
        )
    }

    fun findAll(): List<ComunidadDto> {
        val sql = """
            SELECT id, nombre, direccion, codigo_acceso, estado, creado_en
            FROM comunidades
            ORDER BY id ASC
        """.trimIndent()
        return jdbc.query(sql, rowMapper)
    }

    fun findById(id: Long): ComunidadDto? {
        val sql = """
            SELECT id, nombre, direccion, codigo_acceso, estado, creado_en
            FROM comunidades
            WHERE id = ?
        """.trimIndent()
        return try { jdbc.queryForObject(sql, rowMapper, id) } catch (_: Exception) { null }
    }

    fun findByEstado(estado: Int): List<ComunidadDto> {
        val sql = """
            SELECT id, nombre, direccion, codigo_acceso, estado, creado_en
            FROM comunidades
            WHERE estado = ?
            ORDER BY id ASC
        """.trimIndent()
        return jdbc.query(sql, rowMapper, estado)
    }

    fun create(dto: ComunidadDto): Int {
        val sql = """
            INSERT INTO comunidades
                (nombre, direccion, codigo_acceso, estado)
            VALUES (?, ?, ?, ?)
        """.trimIndent()
        return jdbc.update(sql, dto.getNombre(), dto.getDireccion(), dto.getCodigoAcceso(), dto.getEstado())
    }

    fun update(id: Long, dto: ComunidadDto): Int {
        val sql = """
            UPDATE comunidades
            SET nombre = ?,
                direccion = ?,
                codigo_acceso = ?,
                estado = ?
            WHERE id = ?
        """.trimIndent()
        return jdbc.update(sql, dto.getNombre(), dto.getDireccion(), dto.getCodigoAcceso(), dto.getEstado(), id)
    }

    fun delete(id: Long): Int {
        val sql = "DELETE FROM comunidades WHERE id = ?"
        return jdbc.update(sql, id)
    }
}
