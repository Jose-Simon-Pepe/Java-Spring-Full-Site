package com.web.sporttech.entidades;

import com.web.sporttech.enumeradores.Rol;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Deportista extends Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    List<Objetivo> objetivos;

    public Deportista() {
    }

    public Long getId() {
        return id;
    }

    /*
    no escribir métodos get/set para atributo id
    el atributo id es trabajado desde el padre (usuario)
    esta clase ya cuenta con el metodo get id porque lo hereda
     */
    public void setId(Long id) {
        this.id = id;
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos = objetivos;
    }
    
    

    public Deportista(String sexo, String direccion, Long dni, Date fechaNacimiento, String nombre, String apellido, String telefono,
             String nombreUsuario, String email, String password, boolean alta) {

        super(dni, fechaNacimiento, nombre, apellido, telefono, nombreUsuario, email, password, alta, Rol.DEPORTISTA, sexo, direccion);
      objetivos = new ArrayList<>();
    }
}
