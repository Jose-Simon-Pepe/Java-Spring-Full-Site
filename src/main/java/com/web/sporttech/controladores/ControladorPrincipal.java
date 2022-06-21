package com.web.sporttech.controladores;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.entidades.Usuario;
import com.web.sporttech.enumeradores.Rol;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.servicios.DeportistaServicio;
import com.web.sporttech.servicios.EntrenadorServicio;
import com.web.sporttech.servicios.UsuarioServicio;
import com.web.sporttech.utilidades.Utiles;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ControladorPrincipal {

    @Autowired
    private DeportistaServicio deportistaServicio;
    @Autowired
    private EntrenadorServicio entrenadorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/index")
    public String index(Model modelo, @RequestParam(required = false) String login, HttpSession sesion) {
//        if (sesion != null) {
//            System.out.println("LOGUEO OK");
//        }
        Usuario usuario = null;
        try{
            usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            modelo.addAttribute("usuario", usuario);
        }catch(ErrorServicio e){
            modelo.addAttribute("error", e.getMessage());
        }
//        
//        modelo.addAttribute("usuario", usuario);
        return "index.html";
    }

    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login(Model modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) {

        if (error != null) {
            modelo.addAttribute("estado", "usuario o contraseña incorrectos");
            System.out.println("Hubo un error");
        }
        if (logout != null){
            modelo.addAttribute("estado", "Sesion cerrada con exito");
        }
        return "login.html";
    }

    @GetMapping("/login-successfull")
    public String loginDate(HttpSession sesion) {
        try {
            LocalDate hoy = LocalDate.now();
//            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            
            return "redirect:/usuario/panel-control";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/about")
    public String about() {
        return "about.html";
    }

    @GetMapping("/contacto")
    public String contacto(){
        return "contacto.html";
    }
    
    @GetMapping("/registro")
    public String register() {
        return "register.html";
    }

    @PostMapping("/registro")
    public String registro(Model modelo,
            //atributos usuario del constructor

            Long dni,@RequestParam(required = false) String fechaNacimiento, String nombre, String apellido, String telefono,
            String nombreUsuario, String email, String contraseña, boolean alta,
            String tipoUsuario, String sexo, String direccion, Integer edad
    ) throws ErrorServicio {
        try {
            LocalDate fechaNacimientoParseada = Utiles.FechaStringALocalDate(fechaNacimiento); //se envian los datos para la creacion de usuario en servicio usuario
            usuarioServicio.crearPorRol(dni, fechaNacimientoParseada, nombre, apellido, telefono,
                    nombreUsuario, email, contraseña, alta,
                    tipoUsuario, sexo, direccion);
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }

        return "login.html";
    }

    //añado un comentario mas
}
