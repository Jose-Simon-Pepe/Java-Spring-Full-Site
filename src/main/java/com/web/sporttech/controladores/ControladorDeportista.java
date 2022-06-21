package com.web.sporttech.controladores;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.RutinaDeportista;
import com.web.sporttech.entidades.Usuario;
import com.web.sporttech.enumeradores.Rol;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.servicios.RutinaDeportistaServicio;
import com.web.sporttech.servicios.DeportistaServicio;
import com.web.sporttech.servicios.UsuarioServicio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@RequestMapping("/usuario/deportista")
public class ControladorDeportista {

    @Autowired
    private RutinaDeportistaServicio rutinaDepoServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private DeportistaServicio deportistaServicio;

    @PostMapping("guardar-comentarios/{idRutina}")
    public String guardarComentarios(Model modelo, HttpSession sesion, @PathVariable Long idRutina, @RequestParam(required = false) Boolean checkRealizada, @RequestParam(required = false) String complejidad, @RequestParam(required = false) String describir, @RequestParam(required = false) String sentir) {
        String comentarios = complejidad + "/" + describir + "/" + sentir;
        rutinaDepoServicio.guardarComentarios(idRutina, checkRealizada, comentarios);
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Deportista deportista = usuarioServicio.devolverDeportistaPorIdUsuario(usuario.getId());
            modelo.addAttribute("rutinas", rutinaDepoServicio.obtenerRutinasPorDeportista(deportista));
//            modelo.addAttribute("viendoMisRutinasEntrenador", false);
            if(rutinaDepoServicio.obtenerRutinasPorDeportista(deportista).isEmpty()){
                modelo.addAttribute("listaVacia", true);
            }

            modelo.addAttribute("viendoRutinasTerminadas", false);
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }

        return "inicio_deportista.html";
    }

    @GetMapping("ver-rutinas-terminadas")
    public String verRutinasFinalizadas(Model modelo, HttpSession sesion){
        try{
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Deportista deportista = usuarioServicio.devolverDeportistaPorIdUsuario(usuario.getId());
            modelo.addAttribute("rutinas", rutinaDepoServicio.obtenerRutinasFinalizadasPorDeportista(deportista));
            modelo.addAttribute("deportista", deportista);
            modelo.addAttribute("viendoRutinasTerminadas", true);
        }catch(ErrorServicio e){
            modelo.addAttribute("error", e.getMessage());
        }
        
        return "inicio_deportista.html";
    }
    
    @GetMapping("ver-objetivos")
    public String verObjetivos(Model modelo, HttpSession sesion) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Deportista deportista = null;
            if (usuario.getMyRol() == Rol.DEPORTISTA) {
                deportista = deportistaServicio.buscarPorDNI(usuario.getDni());
                modelo.addAttribute("objetivos", deportista.getObjetivos());
            }
//            Deportista deportista = usuarioServicio.devolverDeportistaPorIdUsuario(usuario.getId());
            if(deportista.getObjetivos().isEmpty()){
                modelo.addAttribute("listaVacia", true);
            }
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }
        return "objetivos.html";
    }

    
}
