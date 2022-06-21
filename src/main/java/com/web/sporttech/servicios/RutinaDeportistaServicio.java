package com.web.sporttech.servicios;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.entidades.RutinaDeportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.repositorios.RutinaDeportistaRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaDeportistaServicio {
    @Autowired
    private RutinaDeportistaRepositorio repo;
    
    public List<RutinaDeportista> obtenerRutinasPorDeportistaYEntrenador(Deportista deportista,Entrenador entrenador){
        return repo.obtenerRutinasPorDeportistaYEntrenador(deportista,entrenador);
    }
    
    public List<RutinaDeportista> obtenerRutinasPorDeportista(Deportista deportista){
        return repo.obtenerRutinasPorDeportista(deportista);
    }
    
    public List<RutinaDeportista> obtenerRutinasFinalizadasPorDeportista(Deportista deportista){
        return repo.obtenerRutinasFinalizadasPorDeportista(deportista);
    }
    
    public void cargar(Deportista deportista, Rutina rutina, String dia, Entrenador entrenador){
        RutinaDeportista rutinaDeportista = new RutinaDeportista();
        rutinaDeportista.setDeportista(deportista);
        rutinaDeportista.setRutina(rutina);
        rutinaDeportista.setDia(dia);
        rutinaDeportista.setEntrenador(entrenador);
//        System.out.println(dia);
        repo.save(rutinaDeportista);
    }
    
    public void guardarComentarios(Long idRutinaDepo, Boolean realizada, String comentarios){
        RutinaDeportista rutinaDepo = repo.getById(idRutinaDepo);
        rutinaDepo.setRealizada(realizada);
        rutinaDepo.setComentarios(comentarios);
        repo.save(rutinaDepo);
    }
    
    public void borrarPorEntrenadorYDepo(Entrenador entrenador, Deportista deportista){
        repo.borrarPorEntrenadorYDepo(entrenador, deportista);
    }
    
    public void borrarPorID(Long idRutinaDepo){
        repo.deleteById(idRutinaDepo);
    }
    
    public RutinaDeportista obtenerPorId(Long idRutinaDepo){
        return repo.getById(idRutinaDepo);
    }
}
