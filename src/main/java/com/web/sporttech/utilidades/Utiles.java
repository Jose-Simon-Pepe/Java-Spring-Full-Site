package com.web.sporttech.utilidades;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class Utiles {
    public static ZoneId defaultZoneId = ZoneId.systemDefault();
    
    public static Date localDateADate(LocalDate localFecha){
        return Date.from(localFecha.atStartOfDay(Utiles.defaultZoneId).toInstant());
    }
    
    public static LocalDate FechaStringALocalDate(String fecha){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimientoParseada = LocalDate.parse(fecha, fmt);
        return fechaNacimientoParseada;
    }
}
