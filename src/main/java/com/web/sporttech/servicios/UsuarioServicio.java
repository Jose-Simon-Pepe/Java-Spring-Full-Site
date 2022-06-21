package com.web.sporttech.servicios;

import com.web.sporttech.controladores.ControladorUsuario;
import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.entidades.Usuario;
import com.web.sporttech.enumeradores.Rol;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.repositorios.DeportistaRepositorio;
import com.web.sporttech.repositorios.EntrenadorRepositorio;
import com.web.sporttech.repositorios.UsuarioRepositorio;
import com.web.sporttech.utilidades.Utiles;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;
    @Autowired
    private DeportistaServicio deportistaServicio;
    @Autowired
    private EntrenadorServicio entrenadorServicio;

    public Entrenador devolverEntrenadorPorIdUsuario(Long idUsuario) throws ErrorServicio {
        Usuario usuario = usuarioRepo.getById(idUsuario);
        Entrenador entrenador = entrenadorServicio.buscarPorEmail(usuario.getEmail());
        return entrenador;
    }

    public Deportista devolverDeportistaPorIdUsuario(Long idUsuario) throws ErrorServicio {
        Usuario usuario = usuarioRepo.getById(idUsuario);
        Deportista deportista = deportistaServicio.buscarPorDNI(usuario.getDni());
        return deportista;
    }

    public int calcularEdad(LocalDate fechaNacimiento, LocalDate fechaActual) {
        int edad = 0;
        if (fechaNacimiento != null) {

            Period periodo = Period.between(fechaNacimiento, fechaActual); //para poder calcular el período
            edad = periodo.getYears(); // y obtener los años
            return edad;
        } else {
            return edad;
        }
    }

    public Usuario obtenerUsuarioPorSesion(HttpSession sesion) throws ErrorServicio {
        Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
        if (usuario != null) {
            return usuario;
        } else {
            throw new ErrorServicio("Tiene que iniciar sesion");
        }
    }

    public Usuario obtenerUsuarioPorId(Long idUsuario) throws ErrorServicio {
        Usuario usuario = usuarioRepo.getById(idUsuario);
        if (usuario != null) {
            return usuario;
        } else {
            throw new ErrorServicio("No se encontro el usuario");
        }
    }

    public void crearPorRol(Long dni, LocalDate fechaNacimiento, String nombre, String apellido, String telefono,
            String nombreUsuario, String email, String contraseña, boolean alta,
            String tipoUsuario, String sexo, String direccion
    ) throws ErrorServicio {

        //creamos encriptador
        BCryptPasswordEncoder encript = new BCryptPasswordEncoder();
        //creamos y seteamos la instancia usuario
        //persistimos al usuario creado
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        usuario.setPassword(encript.encode(contraseña));
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setSexo(sexo);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        Date fechaNacimientoDate = Utiles.localDateADate(fechaNacimiento);
        usuario.setFechaNacimiento(fechaNacimientoDate);
        usuario.setAlta(Boolean.TRUE);
        //creamos los subtipos
        if (tipoUsuario.equals("e")) {
            entrenadorServicio.crearEntrenador(fechaNacimiento, dni, nombre, apellido, telefono,
                    nombreUsuario, email, usuario.getPassword(), true, sexo, direccion);
            usuario.setMyRol(Rol.ENTRENADOR);
        }
        if (tipoUsuario.equals("d")) {
            deportistaServicio.crearDeportista(sexo, direccion, dni, fechaNacimiento, nombre, apellido, telefono,
                    nombreUsuario, email, usuario.getPassword(), true);
            usuario.setMyRol(Rol.DEPORTISTA);
        }
        usuarioRepo.save(usuario);
    }

    public void actualizar(Long idDeQuienActualiza, Long dni, String nombre, String apellido, String telefono,
            String nombreUsuario, String sexo, String direccion, String fechaNacimiento
    ) throws ErrorServicio {
        LocalDate fechaNacimientoLocal = Utiles.FechaStringALocalDate(fechaNacimiento);
        Date fechaNacimientoDate = Utiles.localDateADate(fechaNacimientoLocal);
        
        Optional<Usuario> usuarioAActualizar = usuarioRepo.findById(idDeQuienActualiza);
        if (usuarioAActualizar.isPresent()) {
            Usuario aActualizar = usuarioAActualizar.get();
            aActualizar.setNombreUsuario(nombreUsuario);
            aActualizar.setNombre(nombre);
            aActualizar.setApellido(apellido);
            aActualizar.setDni(dni);
            aActualizar.setSexo(sexo);
            aActualizar.setTelefono(telefono);
            aActualizar.setDireccion(direccion);
            aActualizar.setFechaNacimiento(fechaNacimientoDate);
            //creamos los subtipos
            if (aActualizar.getMyRol() == Rol.ENTRENADOR) {
                entrenadorServicio.actualizar(dni, nombre, apellido, telefono,
                        nombreUsuario, aActualizar.getEmail(), sexo, direccion, fechaNacimientoLocal);
            }
            if (aActualizar.getMyRol() == Rol.DEPORTISTA) {
                deportistaServicio.actualizar(dni, nombre, apellido, telefono,
                        nombreUsuario, aActualizar.getEmail(), sexo, direccion, fechaNacimientoLocal);
            }
            usuarioRepo.save(aActualizar);
        }
    }

    public Usuario encontrarSinRolUsuario(Long idUsuario) {
        try {
            Deportista deportista = devolverDeportistaPorIdUsuario(idUsuario);
            Entrenador entrenador = devolverEntrenadorPorIdUsuario(idUsuario);
//            System.out.println("DEPORTISTA: " + deportista);
//            System.out.println("ENTRENADOR: " + entrenador);
            if (deportista != null) {
                return deportista;
            }
            if (entrenador != null) {
                return entrenador;
            }
            if (entrenador != null && deportista != null) {
                throw new ErrorServicio("Duplicaci\u00f3n de usuario");
            }
            if (entrenador == null && deportista == null) {
                throw new ErrorServicio("El usuario no existe en la db");
            } else {
                throw new ErrorServicio("Error interno");
            }
        } catch (ErrorServicio es) {
            return null;
        }
    }

    public String encontrarRolUsuario(Long idUsuario, ControladorUsuario controladorUsuario) {
        try {
            Deportista deportista = devolverDeportistaPorIdUsuario(idUsuario);
            Entrenador entrenador = devolverEntrenadorPorIdUsuario(idUsuario);
            if (deportista != null) {
                return "DEPORTISTA";
            }
            if (entrenador != null) {
                return "ENTRENADOR";
            }
            if (entrenador != null && deportista != null) {
                throw new ErrorServicio("Duplicaci\u00f3n de usuario");
            }
            if (entrenador == null && deportista == null) {
                throw new ErrorServicio("El usuario no existe en la db");
            } else {
                return null;
            }
        } catch (ErrorServicio es) {
            System.out.println(es.getMessage());
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.buscarPorEmaiList(email).get(0);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getMyRol()));

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = attr.getRequest().getSession(true);
            sesion.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);
            System.out.println(user);

            return user;
        } else {
            return null;
        }
    }
}
