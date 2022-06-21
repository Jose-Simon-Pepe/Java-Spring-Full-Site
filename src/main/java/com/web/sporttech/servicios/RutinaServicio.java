
package com.web.sporttech.servicios;

import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.repositorios.EntrenadorRepositorio;
import com.web.sporttech.repositorios.RutinaRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaServicio {
    
    @Autowired
    RutinaRepositorio repo;
    @Autowired
    EntrenadorRepositorio entrenadorRepo;
    
    public void save(String nombre, String descripcion, Entrenador entrenador){
        Rutina rutina = new Rutina( nombre, descripcion);        
        repo.save(rutina);
        
        List<Rutina> rutinas;
        try{
            rutinas = entrenador.getRutinas();
        }catch(Exception e){
            rutinas = new ArrayList<>();
        }
        rutinas.add(rutina);
        entrenador.setRutinas(rutinas);
        
        entrenadorRepo.save(entrenador);
    }
    
    public Rutina buscarRutinaPorNombre(String nombre){
        return repo.buscarRutinaPorNombre(nombre);
    }
    
    public void editarRutina(Long idRutina, String nombre, String descripcion){
        Rutina rutina = buscarRutinaPorID(idRutina);
        rutina.setNombre(nombre);
        rutina.setDescripcion(descripcion);
        repo.save(rutina);
    }
    
    public Rutina buscarRutinaPorID(Long id){
        return repo.getById(id);
    }
    
    private void validator()throws ErrorServicio{
        
    }
    

}
