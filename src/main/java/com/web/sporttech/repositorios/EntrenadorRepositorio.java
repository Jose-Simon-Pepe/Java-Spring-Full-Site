package com.web.sporttech.repositorios;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Rutina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepositorio extends JpaRepository<Entrenador,Long>{
    @Query("SELECT e.deportistas FROM Entrenador e WHERE e.id = :id")
    public List<Deportista> misDeportistas(@Param("id") Long id);
    
    @Query("SELECT e FROM Entrenador e WHERE e.email = :email")
    public Entrenador buscarPorEmail(@Param("email") String email);
    
    @Query("SELECT e.rutinas FROM Entrenador e WHERE e.id = :id")
    public List<Rutina> misRutinas(@Param("id") Long id);
    
       //actualizar edad
    @Modifying
    @Query("UPDATE Entrenador e SET edad= :edadActualizada WHERE e.id = :id")
    public void actualizarEdad(@Param("id") Long id, @Param("edadActualizada") int edadActualizada);

}
