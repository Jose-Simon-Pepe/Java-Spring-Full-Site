package com.web.sporttech.servicios;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.repositorios.DeportistaRepositorio;
import com.web.sporttech.repositorios.EntrenadorRepositorio;
import com.web.sporttech.utilidades.Utiles;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class EntrenadorServicio {

    //crud
    @Autowired
    private EntrenadorRepositorio repo;

    @Autowired
    private RutinaServicio rutinaServicio;

    @Autowired
    private DeportistaRepositorio deportistaRepo;

    @Autowired
    private RutinaDeportistaServicio rutinaDeportistaServicio;
    
    @Autowired
    private RutinaDeportistaServicio rutinaDepoServicio;

    public List<Deportista> verMisDeportistas(Long idEntrenador) {
        return repo.misDeportistas(idEntrenador);
    }
    
     public void actualizarEdad(Long dni, int edadActualizada){
        repo.actualizarEdad(dni,edadActualizada);
    }
     
     public void actualizar(Long dni, String nombre, String apellido, String telefono,
            String nombreUsuario, String email, String sexo, String direccion, LocalDate fechaNacimiento){
         
        int edad = calcularEdad(fechaNacimiento, LocalDate.now());
         System.out.println("EMAIL: "+email);
        Entrenador aActualizar = buscarPorEmail(email);
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
     
    public Entrenador buscarPorId(Long id) {
        return repo.getById(id);
    }

    public Entrenador buscarPorEmail(String email) {
        return repo.buscarPorEmail(email);
    }
      public Entrenador guardar(Entrenador aGuardar){
        return repo.save(aGuardar);
    }
    

    public List<Rutina> rutinas(Entrenador entrenador) {
        return entrenador.getRutinas();
    }

    //asignamos una rutina que un entrenador posee a un deportista
    public void asignarRutina(String[][] rutinas, Deportista deportista, Entrenador entrenador) throws ErrorServicio {

        for (int i = 0; i < rutinas.length; i++) {
            String nombreRutina = rutinas[i][0];
            String diaRutina = rutinas[i][1].toUpperCase();
            Rutina rutina = buscarRutinaPorNombre(nombreRutina, entrenador);

//            Dia dia = new Dia();
//            dia.setDia(diaRutina);
//            diaRepo.save(dia);

            rutinaDeportistaServicio.cargar(deportista, rutina, diaRutina, entrenador);
        }
// 
    }

    public Rutina buscarRutinaPorNombre(String nombre, Entrenador entrenador){
        boolean encontrado = false;
        Rutina rutinaEncontrada = null;
        
        while(encontrado == false){
            for (Rutina rutina : entrenador.getRutinas()) {
                if(rutina.getNombre().equals(nombre)){
                    encontrado = true;
                    rutinaEncontrada = rutina;
                }
            }
        }
        return rutinaEncontrada;
    }
    
    public void agregarDeportista(Entrenador entrenador, Deportista deportista) throws ErrorServicio {
        List<Deportista> deportistas = entrenador.getDeportistas();
        if (deportistas.contains(deportista)) {
            throw new ErrorServicio("Ya existe deportista. "+deportista.getNombre());
            
        }else{
            deportistas.add(deportista);
            entrenador.setDeportistas(deportistas);
            repo.save(entrenador);
        }
    }

    public void borrarDeportista(Entrenador entrenador, Deportista deportista){
        rutinaDeportistaServicio.borrarPorEntrenadorYDepo(entrenador, deportista);
        entrenador.getDeportistas().remove(deportista);
        repo.save(entrenador);
    }
    
    public void borrarRutina(Entrenador entrenador, Rutina rutina){
        List<Rutina> rutinas = entrenador.getRutinas();
        rutinas.remove(rutina);
        repo.save(entrenador);
    }
    
    //crud
    public Entrenador crearEntrenador(LocalDate fechaNacimiento, 
            Long dni, String nombre, String apellido, String telefono, 
            String nombreUsuario, String email, String password, boolean alta, String sexo, String direccion) throws ErrorServicio {
        try {
            //validamos el input
       
            //seteamos como int la edad
            LocalDate hoy = LocalDate.now(); //obtenemos la fecha de hoy
            int edad = calcularEdad(fechaNacimiento, hoy);
            //persistimos una instancia de deportista con la pass encriptada y la edad calculada
            Date fechaDate = Utiles.localDateADate(fechaNacimiento);
            Entrenador e = new Entrenador(fechaDate,
                    dni, nombre, apellido, telefono,
                    nombreUsuario, email, password, alta, sexo, direccion);
            e.setEdad(edad);
            return repo.save(e);
            
            
        } catch (Exception e) {
            throw new ErrorServicio(e.getMessage());
        }
    }
    
    

    private void validarDatosInstancia(LocalDate fechaNacimiento, String deporte, List<Deportista> deportistas,
            Long dni, String nombre, String apellido, String telefono,
            String nombreUsuario, String email, String password, boolean alta) throws ErrorServicio {
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

}
