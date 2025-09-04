package com.comunidad.proyecto1.Services.Usuarios

import com.comunidad.proyecto1.Dtos.Usuarios.UsuariosDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.jdbc.core.RowMapper
@Service
class UsuariosService {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    private var rowMapper= RowMapper{rs, _ -> UsuariosDto(
        id = rs.getLong("id"),
        nombre=rs.getString("nombre"),
        email = rs.getString("email"),
        clave = rs.getString("clave"),
        torre = rs.getString("torre"),
        apartamento = rs.getString("apartamento"),
        permiso_id = rs.getLong("permiso_id"),
        comunidad_id = rs.getLong("comunidad_id"),
        estado = rs.getInt("estado"),
        creado_en = rs.getDate("creado_en").toLocalDate()
    ) }
    fun getALl():List<UsuariosDto>{
        var sql="SELECT * FROM usuarios"
        val filas =jdbcTemplate.query(sql,rowMapper)
        return filas
    }
    fun getById(id:Long): UsuariosDto?{
        var sql="SELECT * FROM usuarios WHERE id=?"
        return try {
            jdbcTemplate.queryForObject(sql,rowMapper,id)
        }catch(e: Exception){
            null
        }
    }
    // Esta funcion fue creada para no ser consumirla por HTTP si no, para manejarla internamente cundo creamos un permiso y poder obtener el id
    fun getMaxId(): Long?{
        var sql="SELECT max(id) as id FROM usuarios"

        val maxRowMapper = RowMapper { rs, _ ->
            rs.getLong("id")
        }
        return try{ jdbcTemplate.queryForObject(sql, maxRowMapper)}catch (e: Exception) {null}

    }
    fun getEstados(id:Int): List<UsuariosDto>{
        var sql="SELECT * FROM usuarios WHERE estado=?"
        return jdbcTemplate.query(sql,rowMapper,id)
    }
    fun getPermisos(id:Int): List<UsuariosDto>{
        var sql="SELECT * FROM usuarios WHERE permiso_id=?"
        return jdbcTemplate.query(sql,rowMapper,id)
    }
    fun create(usuariosDto: UsuariosDto):Int{
        var sql="INSERT INTO usuarios(nombre,email,clave,torre,apartamento,permiso_id,comunidad_id,estado,creado_en) values(?,?,aes_encrypt(?,?),?,?,?,?,?,now())"
        var filas = jdbcTemplate.update (sql,
            usuariosDto.getNombre(),
            usuariosDto.getEmail(),
            usuariosDto.getClave(),
            usuariosDto.getEmail(),
            usuariosDto.getTorre(),
            usuariosDto.getApartamento(),
            usuariosDto.getPermisoId(),
            usuariosDto.getComunidadId(),
            usuariosDto.getEstado()
        )
        return filas
    }
    fun update(id:Long,usuarios:UsuariosDto):Int{
        var sql="UPDATE usuarios SET  nombre=?, email=?, clave=aes_encrypt(?,?), torre=?, apartamento=?,permiso_id=?,comunidad_id=?, estado=?  WHERE id=?"
        var filas = jdbcTemplate.update (
            sql,
            usuarios.getNombre(),
            usuarios.getEmail(),
            usuarios.getClave(),
            usuarios.getEmail(),
            usuarios.getTorre(),
            usuarios.getApartamento(),
            usuarios.getPermisoId(),
            usuarios.getComunidadId(),
            usuarios.getEstado(),
            id)
        return filas
    }
    fun delete(id:Long):Int{
        var sql="DELETE FROM usuarios WHERE id=?"
        val filas = jdbcTemplate.update(sql,id)
        return filas
    }
    fun getFiltro(nombre:String): List<UsuariosDto> {
        var sql = "SELECT * FROM usuarios WHERE nombre LIKE ? OR email LIKE ? OR torre LIKE ?"
        return try {
            jdbcTemplate.query(sql, rowMapper,"%$nombre%","%$nombre%","%$nombre%")
        } catch (e: Exception) {
            emptyList()
        }

    }
    fun updateEstado(id:Long,usuarios: UsuariosDto):Int{
        var sql="UPDATE usuarios SET  estado=?  WHERE id=?"
        var filas = jdbcTemplate.update (
            sql,
            usuarios.getEstado(),
            id)
        return filas
    }
    fun updatePermiso(id:Long,usuarios: UsuariosDto):Int{
        var sql="UPDATE usuarios SET  permiso_id=?  WHERE id=?"
        var filas = jdbcTemplate.update (
            sql,
            usuarios.getPermisoId(),
            id)
        return filas
    }
    fun login(email: String, clavePlana: String): UsuariosDto? {
        val sql = "SELECT * FROM usuarios WHERE email = ? AND AES_DECRYPT(clave, ?) = ?"

        return try {
            jdbcTemplate.queryForObject(sql, rowMapper, email, email, clavePlana)
        } catch (e: Exception) {
            println("Error en autenticación: ${e.message}")
            null
        }
    }
}