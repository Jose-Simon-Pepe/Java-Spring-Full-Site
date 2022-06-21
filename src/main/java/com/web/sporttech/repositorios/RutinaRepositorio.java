/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.sporttech.repositorios;

import com.web.sporttech.entidades.Rutina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author simon
 */
public interface RutinaRepositorio extends JpaRepository<Rutina, Long>{
    
    
    //buscamos las rutinas de un entrenador
    @Query("select R from Rutina R join Entrenador r WHERE r.id= :id")
    public List<Rutina> buscarRutinaPorEntrenador(@Param("id") Long id);
    
    @Query("SELECT r FROM Rutina r WHERE r.nombre = :nombre")
    public Rutina buscarRutinaPorNombre(@Param("nombre") String nombre);
}
