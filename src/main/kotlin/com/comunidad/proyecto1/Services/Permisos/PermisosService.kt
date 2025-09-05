package com.comunidad.proyecto1.Services.Permisos

import com.comunidad.proyecto1.Dtos.Permisos.PermisosDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class PermisosService {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    private val rowMapper= RowMapper{rs, _ ->PermisosDto(
        id=rs.getLong("id"),
        nombre= rs.getString("nombre"),
        descripcion= rs.getString("descripcion")
    )}
    fun getAll(): List<PermisosDto>{
        var sql="SELECT * FROM permisos"

        return jdbcTemplate.query(sql,rowMapper)
    }
    fun getById(id:Long):PermisosDto?{
        var sql="SELECT * FROM permisos WHERE id=?"
        return try {
            jdbcTemplate.queryForObject(sql,rowMapper,id)
        }catch(e: Exception){
            null
        }
    }
    // Esta funcion fue creada para no consumirla por HTTP si no para manejarla internamente, cundo creamos un permiso y poder obtener el id
    fun getMaxId(): Long?{
        var sql="SELECT max(id) as id FROM permisos"

        val maxRowMapper = RowMapper { rs, _ ->
            rs.getLong("id")
        }
        return try{ jdbcTemplate.queryForObject(sql, maxRowMapper)}catch (e: Exception) {null}

    }
    fun create(permisos:PermisosDto):Int{
        var sql="INSERT INTO permisos(nombre,descripcion) values(?,?)"
        var filas = jdbcTemplate.update (sql,permisos.getNombre(),permisos.getDescripcion())
        return filas
    }
    fun update(id:Long,permisos:PermisosDto):Int{
        var sql="UPDATE permisos SET  nombre=?, descripcion=? WHERE id=?"
        var filas = jdbcTemplate.update (sql,permisos.getNombre(),permisos.getDescripcion(),id)
        return filas
    }
    fun delete(id:Long):Int{
        var sql="DELETE FROM permisos WHERE id=?"
        val filas = jdbcTemplate.update(sql,id)
        return filas
    }
    fun getFiltro(nombre:String): List<PermisosDto> {
        var sql = "SELECT * FROM permisos WHERE nombre LIKE ? OR descripcion LIKE ?"
        return try {
            jdbcTemplate.query(sql, rowMapper,"%$nombre%","%$nombre%")
        } catch (e: Exception) {
            emptyList()
        }

    }

}