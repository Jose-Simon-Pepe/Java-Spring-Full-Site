package com.web.sporttech.entidades;

import com.web.sporttech.enumeradores.Rol;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // se creará una tabla por cada subclase. duplicidad de datos en db
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Long dni;
    
    @Temporal(TemporalType.DATE)
    @Nullable
    private Date fechaNacimiento;
    
    private String nombre;
    private String apellido;
    private String telefono;
    private Integer edad;
    private String nombreUsuario;
    private String email;
    private String sexo;
    private String direccion;
    @Enumerated(EnumType.STRING)
    private Rol myRol;
    private String password;
    private boolean alta;

    public Usuario(Long dni,Date fechaNacimiento, String nombre, String apellido, String telefono, String nombreUsuario, String email, String password, boolean alta, Rol myRol, String sexo, String direccion) {
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.edad = edad;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.alta = alta;
        this.myRol = myRol;
        this.sexo = sexo;
        this.direccion = direccion;
    }
    
    public Usuario() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    
    

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getMyRol() {
        return myRol;
    }

    public void setMyRol(Rol myRol) {
        this.myRol = myRol;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }
}
