package com.comunidad.proyecto1.Dtos.Avisos

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class AvisosDto(
    var id: Long = 0,
    var usuarioId: Long = 0,
    var comunidadId: Long = 0,
    var categoriaId: Long = 0,
    var titulo: String = "",
    var descripcion: String = "",

    @JsonFormat(pattern = "yyyy-MM-dd")
    var fechaPublicacion: LocalDate = LocalDate.now(),

    @JsonFormat(pattern = "yyyy-MM-dd")
    var fechaLimite: LocalDate? = null,

    var ubicacion: String? = null,
    var estado: Int = 1,
    var urgente: Int = 1
)
