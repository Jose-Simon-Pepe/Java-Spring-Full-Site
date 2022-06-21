
package com.web.sporttech.servicios;

import com.web.sporttech.entidades.Objetivo;
import com.web.sporttech.repositorios.ObjetivoRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetivoServicio {
    
    @Autowired
    ObjetivoRepositorio repo;
    
   public Objetivo crearYGuardar(Date fechaComienzo, Date fechaFinalizacion, String objetivos){
       return repo.save(new Objetivo(fechaComienzo, fechaFinalizacion, objetivos));
   } 
    
   public Objetivo buscarPorId(Long id) throws Exception{
       Optional<Objetivo> buscado = repo.findById(id);
       if (buscado.isPresent()) {
             System.out.println("existe y lo encuentra");
           return buscado.get();
       }else{
           throw new Exception("No existe el objetivo indicado");
       }
   }
   
   public void eliminar(Long id) throws Exception{
     
       repo.delete(buscarPorId(id));
   }
   
   public void eliminar(Objetivo aEliminar) throws Exception{
       repo.delete(aEliminar);
   }

}
