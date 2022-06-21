package com.web.sporttech.entidades;

import com.web.sporttech.enumeradores.Rol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Entrenador extends Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deporte;
    @ManyToMany
    private List<Deportista> deportistas;
    @ManyToMany
    private List<Rutina> rutinas;

    public Entrenador(Date fechaNacimiento, 
            Long dni, String nombre, String apellido, String telefono, 
            String nombreUsuario, String email, String password, boolean alta, String sexo, String direccion) {
        super(dni, fechaNacimiento, nombre, apellido, telefono, nombreUsuario, email, password, alta, Rol.ENTRENADOR, sexo, direccion);
        this.deporte = deporte;
        deportistas = new ArrayList<>();
        rutinas = new ArrayList<>();
    }
    
    public Entrenador() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public List<Rutina> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<Rutina> rutinas) {
        this.rutinas = rutinas;
    }
    
    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public List<Deportista> getDeportistas() {
        return deportistas;
    }

    public void setDeportistas(List<Deportista> deportistas) {
        this.deportistas = deportistas;
    }
}
