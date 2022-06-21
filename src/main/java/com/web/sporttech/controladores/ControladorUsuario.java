package com.web.sporttech.controladores;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.RutinaDeportista;
import com.web.sporttech.entidades.Usuario;
import com.web.sporttech.enumeradores.Rol;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.servicios.DeportistaServicio;
import com.web.sporttech.servicios.EntrenadorServicio;
import com.web.sporttech.servicios.RutinaDeportistaServicio;
import com.web.sporttech.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private RutinaDeportistaServicio rutinaDepoServicio;
    @Autowired
    private EntrenadorServicio entrenadorServicio;
    @Autowired
    private DeportistaServicio deportistaServicio;

    @GetMapping("/panel-control")
    public String verPanelControl(Model modelo, HttpSession sesion) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            modelo.addAttribute("usuario", usuarioServicio.obtenerUsuarioPorId(usuario.getId()));
            if (usuario.getMyRol() == Rol.DEPORTISTA) {
                Deportista deportista = usuarioServicio.devolverDeportistaPorIdUsuario(usuario.getId());
                System.out.println("DEPORTISTA: "+deportista);
                modelo.addAttribute("deportista", deportista);
                List<RutinaDeportista> rutinasDeportista = rutinaDepoServicio.obtenerRutinasPorDeportista(deportista);
                
                if(rutinasDeportista.isEmpty()){
                    modelo.addAttribute("listaVacia", true);
                }
                
                System.out.println("RUTINAS DEPORTISTA: "+rutinasDeportista);
                modelo.addAttribute("rutinas", rutinaDepoServicio.obtenerRutinasPorDeportista(deportista));
                modelo.addAttribute("viendoRutinasTerminadas", false);
                return "inicio_deportista.html";
            } else {
                Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
                System.out.println("ENTRENADOR: "+entrenador);
                modelo.addAttribute("entrenador", entrenador);
                modelo.addAttribute("deportistas", entrenador.getDeportistas());
                return "entrenador.html";
            }
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
            System.out.println("ERROR: "+ e.getMessage());
            return "index.html";
        }
    }
    
    @GetMapping("/ver-rutinas")
    public String verRutinas(Model modelo, HttpSession sesion){
        try{
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            System.out.println("USUARIO: "+usuario.getId());
            Deportista deportista = usuarioServicio.devolverDeportistaPorIdUsuario(usuario.getId());
            System.out.println("DEPORTISTA: "+deportista);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
            System.out.println("ENTRENADOR: "+entrenador);
            
            if(deportista != null){
                modelo.addAttribute("rutinas", rutinaDepoServicio.obtenerRutinasPorDeportista(deportista));
                modelo.addAttribute("viendoMisRutinasEntrenador", false);
            }
            if(entrenador != null){
                modelo.addAttribute("rutinas", entrenadorServicio.rutinas(entrenador));
                modelo.addAttribute("entrenador", entrenador);
                modelo.addAttribute("viendoMisRutinasEntrenador", true);
                System.out.println(entrenadorServicio.rutinas(entrenador));
            }
        }catch(ErrorServicio e){
            modelo.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
        }
        return "rutinas.html";
    }
    
    @GetMapping("/ver-rutinas-deportista/{idDeportista}")
    public String verRutinasDeportista(Model modelo, HttpSession sesion, @PathVariable Long idDeportista){
        try{
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Deportista deportista = deportistaServicio.buscarPorId(idDeportista);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
            modelo.addAttribute("rutinas", rutinaDepoServicio.obtenerRutinasPorDeportistaYEntrenador(deportista,entrenador));
            modelo.addAttribute("deportista", deportista);
            modelo.addAttribute("viendoMisRutinasEntrenador", false);
        }catch(ErrorServicio e){
            modelo.addAttribute("error", e.getMessage());
        } 
            
        return "rutinas.html";
    }
    
    @GetMapping("/perfil")
    public String perfil(Model modelo, HttpSession sesion) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Deportista deportista = usuarioServicio.devolverDeportistaPorIdUsuario(usuario.getId());
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());

            if (deportista != null) {
                Usuario userModelo = usuarioServicio.obtenerUsuarioPorId(usuario.getId());
                modelo.addAttribute("deportista", deportista);
                modelo.addAttribute("usuario", userModelo);
            }
            if (entrenador != null) {
                Usuario userModelo = usuarioServicio.obtenerUsuarioPorId(usuario.getId());
                modelo.addAttribute("entrenador", entrenador);
                modelo.addAttribute("usuario", userModelo);
            }

            if (entrenador != null && deportista != null) {
                throw new ErrorServicio("Duplicación de usuario");
            }
            if (entrenador == null && deportista == null) {
                throw new ErrorServicio("El usuario no existe en la db");
            } else {
                throw new ErrorServicio("Error interno");
            }

        } catch (ErrorServicio es) {
            modelo.addAttribute("error", es.getMessage());
        }
        return "perfil.html";
    }
    
    @PostMapping("/actualizar")
    public String actualizar(Model modelo, @RequestParam("idDeQuienActualiza") Long idDeQuienActualiza, Long dni, String nombre, String apellido, String telefono,
            String nombreUsuario, String email, String sexo, String direccion, String fechaNacimiento) {
        try {
            usuarioServicio.actualizar( idDeQuienActualiza, dni,  nombre,  apellido,  telefono, 
                                        nombreUsuario,  sexo,  direccion, fechaNacimiento);
            modelo.addAttribute("estado", "Su perfil fue actualizado con exito");
            
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(idDeQuienActualiza);
            Deportista deportista = usuarioServicio.devolverDeportistaPorIdUsuario(usuario.getId());
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
            
            if (deportista != null) {
                Usuario userModelo = usuarioServicio.obtenerUsuarioPorId(usuario.getId());
                modelo.addAttribute("deportista", deportista);
                modelo.addAttribute("usuario", userModelo);
            }
            if (entrenador != null) {
                Usuario userModelo = usuarioServicio.obtenerUsuarioPorId(usuario.getId());
                modelo.addAttribute("entrenador", entrenador);
                modelo.addAttribute("usuario", userModelo);
            }
            
            return  "redirect:/usuario/perfil";
            
        } catch (ErrorServicio es) {
            modelo.addAttribute("estado", es.getMessage());
            return "perfil.html";
        }
    }

}
