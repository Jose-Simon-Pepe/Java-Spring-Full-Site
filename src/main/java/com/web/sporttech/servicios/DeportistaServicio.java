package com.web.sporttech.servicios;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Objetivo;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.repositorios.DeportistaRepositorio;
import com.web.sporttech.utilidades.Utiles;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class DeportistaServicio {

    @Autowired
    DeportistaRepositorio repo;

    //crud
    public Deportista crearDeportista(String sexo, String direccion, Long dni,
            LocalDate fechaNacimiento, String nombre, String apellido, String telefono,
            String nombreUsuario, String email, String password, boolean alta) throws ErrorServicio {
        try {
            //validamos el input
            LocalDate hoy = LocalDate.now(); //obtenemos la fecha de hoy
            int edad = calcularEdad(fechaNacimiento, hoy);
            //persistimos una instancia de deportista con la pass encriptada y la edad calculada
            Date fechaDate = Utiles.localDateADate(fechaNacimiento);
            Deportista d = new Deportista(sexo, direccion, dni, fechaDate,
                    nombre, apellido, telefono, nombreUsuario, email, password, true);
            d.setEdad(edad);
            return repo.save(d);
        } catch (Exception e) {
            throw new ErrorServicio(e.getMessage());
        }
    }
    
      
     public void actualizar(Long dni, String nombre, String apellido, String telefono,
            String nombreUsuario, String email, String sexo, String direccion, LocalDate fechaNacimiento) throws ErrorServicio{
        
        int edad = calcularEdad(fechaNacimiento, LocalDate.now());
        Deportista aActualizar = buscarPorDNI(dni);
        aActualizar.setDni(dni);
        aActualizar.setNombre(nombre);
        aActualizar.setEdad(edad);
        aActualizar.setApellido(apellido);
        aActualizar.setTelefono(telefono);
        aActualizar.setNombreUsuario(nombreUsuario);
        aActualizar.setSexo(sexo);
        aActualizar.setDireccion(direccion);
        Date fechaNacimientoDate = Utiles.localDateADate(fechaNacimiento);
        aActualizar.setFechaNacimiento(fechaNacimientoDate);
        repo.save(aActualizar);
     }
    
    private int calcularEdad(LocalDate fechaNacimiento, LocalDate fechaActual) {
        int edad = 0;
        if (fechaNacimiento != null) {

            Period periodo = Period.between(fechaNacimiento, fechaActual); //para poder calcular el período
            edad = periodo.getYears(); // y obtener los años
            return edad;
        } else {
            return edad;
        }
    }

    public void actualizarEdad(Long id, int edadActualizada) {
        repo.actualizarEdad(id, edadActualizada);
    }

    public List<Deportista> verDeportistas() {
        return repo.findAll();
    }

    public Deportista buscarPorDNI(Long dni) throws ErrorServicio {
        Deportista buscado = repo.buscarPorDNI(dni);
        if (buscado!=null) {
            return buscado;
        }else{
            return null;
        }

    }

    public Deportista buscarPorId(Long id) {
        return repo.getById(id);
    }

    public Deportista guardar(Deportista aGuardar) {
        return repo.save(aGuardar);
    }

    private void validarDatosInstancia(String sexo, String direccion,
            Long dni, Date fechaNacimiento,
            String nombre, String apellido,
            String telefono, Integer edad,
            String nombreUsuario, String email,
            String password, boolean alta) throws ErrorServicio {
    }

}
