package com.web.sporttech.repositorios;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.entidades.RutinaDeportista;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaDeportistaRepositorio extends JpaRepository<RutinaDeportista,Long>{
   
   @Query("SELECT rd FROM RutinaDeportista rd WHERE rd.deportista = :deportista AND rd.entrenador = :entrenador")
   public List<RutinaDeportista> obtenerRutinasPorDeportistaYEntrenador(@Param("deportista") Deportista deportista, @Param("entrenador") Entrenador entrenador);
   
   @Query("SELECT rd FROM RutinaDeportista rd WHERE rd.deportista = :deportista AND rd.realizada = false")
   public List<RutinaDeportista> obtenerRutinasPorDeportista(@Param("deportista") Deportista deportista);
   
   @Query("SELECT rd FROM RutinaDeportista rd WHERE rd.deportista = :deportista AND rd.realizada = true")
   public List<RutinaDeportista> obtenerRutinasFinalizadasPorDeportista(@Param("deportista") Deportista deportista); 
   
   @Transactional
   @Modifying
   @Query("DELETE FROM RutinaDeportista rd WHERE rd.entrenador = :entrenador AND rd.deportista = :deportista")
   public void borrarPorEntrenadorYDepo(@Param("entrenador") Entrenador entrenador, @Param("deportista") Deportista deportista);
}
