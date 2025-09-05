package com.comunidad.proyecto1.Services.Registros

import com.comunidad.proyecto1.Dtos.Registros.RegistrosDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RegistrosService {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    private var rowMapper= RowMapper{rs,_ -> RegistrosDto(
        id=rs.getLong("id"),
        usuario=rs.getLong("usuario"),
        fecha=rs.getDate("fecha").toLocalDate()
    ) }
    fun getALl():List<RegistrosDto>{
        var sql="SELECT * FROM registros_login"
        val filas =jdbcTemplate.query(sql,rowMapper)
        return filas
    }
    fun getById(id:Long): RegistrosDto?{
        var sql="SELECT * FROM registros_login WHERE id=?"
        return try {
            jdbcTemplate.queryForObject(sql,rowMapper,id)
        }catch(e: Exception){
            null
        }
    }
    // Esta funcion fue creada para no ser consumirla por HTTP si no, para manejarla internamente cundo creamos un permiso y poder obtener el id
    fun getMaxId(): Long?{
        var sql="SELECT max(id) as id FROM registros_login"

        val maxRowMapper = RowMapper { rs, _ ->
            rs.getLong("id")
        }
        return try{ jdbcTemplate.queryForObject(sql, maxRowMapper)}catch (e: Exception) {null}

    }
    fun getUsuario(id:Int): List<RegistrosDto>{
        var sql="SELECT * FROM registros_login WHERE usuario=?"
        return jdbcTemplate.query(sql,rowMapper,id)
    }

    fun create(id:Long):Int{
        var sql="INSERT INTO registros-login(usuario,fecha) values(?,now())"
        var filas = jdbcTemplate.update (sql,
            id
        )
        return filas
    }

    fun delete(id:Long):Int{
        var sql="DELETE FROM registros_login WHERE id=?"
        val filas = jdbcTemplate.update(sql,id)
        return filas
    }
    fun getFechas(fecha: LocalDate,fecha2: LocalDate): List<RegistrosDto> {
        var sql = "SELECT * FROM registros_login WHERE fecha BETWEEN ? AND ?"
        return try {
            jdbcTemplate.query(sql, rowMapper,fecha,fecha2)
        } catch (e: Exception) {
            emptyList()
        }

    }

}