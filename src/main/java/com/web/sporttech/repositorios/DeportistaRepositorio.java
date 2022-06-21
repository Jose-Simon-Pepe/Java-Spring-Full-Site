package com.web.sporttech.repositorios;

import com.web.sporttech.entidades.Deportista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeportistaRepositorio extends JpaRepository<Deportista, Long> {

    @Query("SELECT d FROM Deportista d WHERE d.dni = :dni")
    public Deportista buscarPorDNI(@Param("dni") Long dni);

    //actualizar edad
    @Query("UPDATE Deportista d SET edad='edadActualizada' WHERE d.id = : id")
    public Deportista actualizarEdad(@Param("dni") Long id, @Param("edadActualizada") int edadActualizada);

    //filtrar por edad
//    @Query("SELECT d FROM Deportista d where ")
    //filtrar por nombre (comienza por)
    //filtrar por apellido (comienza por)
    
    
    

}
