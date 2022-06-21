/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.sporttech.repositorios;
import com.web.sporttech.entidades.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author simon
 */
@Repository
public interface ObjetivoRepositorio extends JpaRepository<Objetivo, Long>{
    
}
