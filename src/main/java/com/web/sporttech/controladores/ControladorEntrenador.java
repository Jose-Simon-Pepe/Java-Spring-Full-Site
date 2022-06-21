package com.web.sporttech.controladores;

import com.web.sporttech.entidades.Deportista;
import com.web.sporttech.entidades.Entrenador;
import com.web.sporttech.entidades.Objetivo;
import com.web.sporttech.entidades.Rutina;
import com.web.sporttech.entidades.RutinaDeportista;
import com.web.sporttech.entidades.Usuario;
import com.web.sporttech.excepciones.ErrorServicio;
import com.web.sporttech.servicios.DeportistaServicio;
import com.web.sporttech.servicios.EntrenadorServicio;
import com.web.sporttech.servicios.ObjetivoServicio;
import com.web.sporttech.servicios.RutinaDeportistaServicio;
import com.web.sporttech.servicios.RutinaServicio;
import com.web.sporttech.servicios.UsuarioServicio;
import com.web.sporttech.utilidades.Utiles;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario/entrenador")
public class ControladorEntrenador {

    @Autowired
    private EntrenadorServicio entrenadorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private DeportistaServicio deportistaServicio;
    @Autowired
    private RutinaServicio rutinaServicio;
    @Autowired
    private ObjetivoServicio objetivoServicio;
    @Autowired
    private RutinaDeportistaServicio rutinaDepoServicio;

    @GetMapping("ingresar-rutina/{idDeportista}")
    public String ingresarRutina(Model modelo, HttpSession sesion, @PathVariable Long idDeportista) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Deportista deportista = deportistaServicio.buscarPorId(idDeportista);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());

            modelo.addAttribute("entrenador", entrenador);
            modelo.addAttribute("deportista", deportista);
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }
        return "cargar_rutinas.html";
    }
    
    @GetMapping("borrar-rutina-deportista/{idRutinaDepo}")
    public String borrarRutinaDeportista(Model modelo, HttpSession sesion, @RequestParam("idRutinaDepo") Long idRutinaDepo){
        Entrenador entrenador = null;
        try{
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());   
        }catch(ErrorServicio e){
            modelo.addAttribute("error", e.getMessage());
        }
        RutinaDeportista rutinaDeportista = rutinaDepoServicio.obtenerPorId(idRutinaDepo);
        Deportista deportista = rutinaDeportista.getDeportista();
        rutinaDepoServicio.borrarPorID(idRutinaDepo);
        modelo.addAttribute("rutinas", rutinaDepoServicio.obtenerRutinasPorDeportistaYEntrenador(deportista,entrenador));
        modelo.addAttribute("deportista", deportista);
        modelo.addAttribute("viendoMisRutinasEntrenador", false);
        return "rutinas.html";
    }
    
    @PostMapping("guardar-rutinas")
    public String cargarRutinaDeportista(Model modelo, HttpSession sesion, @RequestParam Long dni, @RequestParam(required = false) String rutinasCargadas) {
        JSONArray rutinasArray = new JSONArray(rutinasCargadas);
        String[][] rutinas = new String[rutinasArray.length()][2];

        for (int i = 0; i < rutinasArray.length(); i++) {
            JSONObject objeto = rutinasArray.getJSONObject(i);
            rutinas[i][0] = objeto.getString("nombreRutina");
            rutinas[i][1] = objeto.getString("diaRutina");
        }

        try {
            Deportista deportista = deportistaServicio.buscarPorDNI(dni);
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
            entrenadorServicio.asignarRutina(rutinas, deportista, entrenador);

            modelo.addAttribute("entrenador", entrenador);
            modelo.addAttribute("deportistas", entrenador.getDeportistas());
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }
        return "entrenador.html";
    }

    @GetMapping("borrar-mi-rutina/{idRutina}")
    public String borrarMiRutina(Model modelo, HttpSession sesion, @PathVariable Long idRutina) {
        Rutina rutina = rutinaServicio.buscarRutinaPorID(idRutina);
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
            entrenadorServicio.borrarRutina(entrenador, rutina);

            modelo.addAttribute("rutinas", entrenadorServicio.rutinas(entrenador));
            modelo.addAttribute("entrenador", entrenador);
            modelo.addAttribute("viendoMisRutinasEntrenador", true);
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }

        return "rutinas.html";
    }
    
    @GetMapping("editar-mi-rutina/{idRutina}")
    public String editarMiRutina(Model modelo, HttpSession sesion, @PathVariable Long idRutina){
        Rutina rutina = rutinaServicio.buscarRutinaPorID(idRutina);
        modelo.addAttribute("rutina", rutina);
        
        modelo.addAttribute("editar", true);
        return "crear_rutinas.html";
    }
    
    @PostMapping("editar-mi-rutina/{idRutina}")
    public String editarMiRutina(Model modelo, HttpSession sesion, @PathVariable Long idRutina, @RequestParam String nombre_rutina, @RequestParam String descripcion){
        Entrenador entrenador = null;
        try{
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
        }catch(ErrorServicio e){
            modelo.addAttribute("error", e.getMessage());
        }
        rutinaServicio.editarRutina(idRutina, nombre_rutina, descripcion);
        modelo.addAttribute("rutinas", entrenadorServicio.rutinas(entrenador));
        modelo.addAttribute("entrenador", entrenador);
        modelo.addAttribute("viendoMisRutinasEntrenador", true);
        return "rutinas.html";
    }

    @GetMapping("cargar-objetivo/{id}")
    public String verCargarObjetivo(@PathVariable Long id, Model modelo) {
        Deportista deportista = deportistaServicio.buscarPorId(id);
        modelo.addAttribute("deportista", deportista);
        return "cargar_objetivo.html";
    }

    @PostMapping("cargar-objetivo/{id}")
    public String cargarObjetivoADeportista(Model model, @PathVariable Long id,
            @RequestParam String comienzo,
            @RequestParam String finalizacion, @RequestParam String objetivo) {
        try {
            System.out.println(comienzo);
            System.out.println(finalizacion);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate comienzoParsed = LocalDate.parse(comienzo, fmt);
            LocalDate finalizacionParsed = LocalDate.parse(finalizacion, fmt);
            Date comienzoDate = Utiles.localDateADate(comienzoParsed);
            Date finalizacionDate = Utiles.localDateADate(finalizacionParsed);
            Deportista aQuienGuardarle = deportistaServicio.buscarPorId(id);
            Objetivo elObjetivo = objetivoServicio.crearYGuardar(comienzoDate, finalizacionDate, objetivo);
            aQuienGuardarle.getObjetivos().add(elObjetivo);
            deportistaServicio.guardar(aQuienGuardarle);
            model.addAttribute("estado", "guardado exitoso");
            model.addAttribute("deportista", aQuienGuardarle);
            return "cargar_objetivo.html";
        } catch (Exception e) {
            model.addAttribute("estado", e.getMessage());
            System.out.println("ERROR: " + e.getMessage());
            return "cargar_objetivo.html";
        }

    }

    @GetMapping("ver-objetivos/{idDeportista}")
    public String verObjetivosMiDeportista(Model modelo, HttpSession sesion, @PathVariable Long idDeportista) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Deportista deportista = deportistaServicio.buscarPorId(idDeportista);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
            modelo.addAttribute("objetivos", deportista.getObjetivos());
            modelo.addAttribute("entrenador", entrenador);
            modelo.addAttribute("deportista", deportista);
            if(deportista.getObjetivos().isEmpty()){
                modelo.addAttribute("listaVacia", true);
            }
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }

        return "objetivos.html";
    }

    @GetMapping("/eliminar-objetivos/{idObjetivo}")
    public String eliminarObjetivoDeDeportista(Model modelo, HttpSession sesion, @RequestParam("idObjetivo") Long idObjetivo, @RequestParam("idDeportista") Long idDeportista) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
            //borramos asociaciones
            System.out.println("reques param es " + idDeportista);
            Deportista duenioDelObjetivo = deportistaServicio.buscarPorId(idDeportista);
            duenioDelObjetivo.getObjetivos().remove(objetivoServicio.buscarPorId(idObjetivo));
            deportistaServicio.guardar(duenioDelObjetivo);
            System.out.println("hecho");
            modelo.addAttribute("objetivos", duenioDelObjetivo.getObjetivos());
            modelo.addAttribute("entrenador", entrenador);
            modelo.addAttribute("deportista", duenioDelObjetivo);
            if(duenioDelObjetivo.getObjetivos().isEmpty()){
                modelo.addAttribute("listaVacia", true);
            }
            //luego el objeto
            objetivoServicio.eliminar(idObjetivo);
            modelo.addAttribute("estado", "Objetivo eliminado con éxito");
            System.out.println("ha sido eliminado. ");

            return "objetivos.html";
        } catch (Exception e) {
            modelo.addAttribute("estado", e.getCause());
            return "objetivos.html";
        }
    }

    @GetMapping("ver-mis-deportistas")
    public String verMisDeportistas(Model modelo, HttpSession sesion) {
        Entrenador entrenador = null;
        Usuario usuario = null;
        try {
            usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("entrenador", entrenador);
        modelo.addAttribute("buscadoVacio", false);
        return "cargar_deportista.html";
    }

    @PostMapping("/buscar-deportista")
    public String buscarDeportista(Model modelo, @RequestParam Long dni) {
        try {
            Deportista buscado = deportistaServicio.buscarPorDNI(dni);
            modelo.addAttribute("buscado", buscado);
            if(buscado == null){
                modelo.addAttribute("buscadoVacio", true);
            }else{
                modelo.addAttribute("buscadoVacio", false);
            }
            
            return "cargar_deportista.html";
        } catch (ErrorServicio es) {
            
            modelo.addAttribute("error", es.getMessage());
            return "cargar_deportista.html";
        }
    }

    @PostMapping("/guardar-deportista")
    public String cargarDeportista(Model modelo, HttpSession sesion, @RequestParam Long dni) {
        try {
            Deportista deportista = deportistaServicio.buscarPorDNI(dni);
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());

            entrenadorServicio.agregarDeportista(entrenador, deportista);

            modelo.addAttribute("usuario", usuario);
            modelo.addAttribute("entrenador", entrenador);
            modelo.addAttribute("deportistas", entrenador.getDeportistas());
            
            return "entrenador.html";
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
            
            return "cargar_deportista.html";
        }
        
    }

    @GetMapping("borrar-deportista/{idDeportista}")
    public String borrarDeportista(Model modelo, HttpSession sesion, @PathVariable Long idDeportista) {
        try {
            Deportista deportista = deportistaServicio.buscarPorId(idDeportista);
            Usuario usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            Entrenador entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());

            entrenadorServicio.borrarDeportista(entrenador, deportista);
            modelo.addAttribute("entrenador", entrenador);
            modelo.addAttribute("deportistas", entrenador.getDeportistas());
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }
        return "entrenador.html";
    }

    @GetMapping("ver-cargar-rutinas")
    public String verCargarRutina(Model modelo, HttpSession sesion) {
        Usuario usuario = null;
        try {
            usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            modelo.addAttribute("editar", false);
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }
        modelo.addAttribute("usuario", usuario);
        return "crear_rutinas.html";
    }

    @PostMapping("cargar-rutina")
    public String cargarRutina(Model modelo, HttpSession sesion, @RequestParam String nombre_rutina, @RequestParam String descripcion) {
        Entrenador entrenador = null;
        Usuario usuario = null;
        try {
            System.out.println("ESTOY ADENTRO");
            usuario = usuarioServicio.obtenerUsuarioPorSesion(sesion);
            entrenador = usuarioServicio.devolverEntrenadorPorIdUsuario(usuario.getId());
        } catch (ErrorServicio e) {
            modelo.addAttribute("error", e.getMessage());
        }
        rutinaServicio.save(nombre_rutina, descripcion, entrenador);
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("entrenador", entrenador);
        modelo.addAttribute("deportistas", entrenador.getDeportistas());
        modelo.addAttribute("rutinas", entrenadorServicio.rutinas(entrenador));
        modelo.addAttribute("viendoMisRutinasEntrenador", true);
        return "redirect:/usuario/ver-rutinas";
    }

//    @PostMapping("modificar")
//    public String modificar(Model modelo, @RequestParam(required=false) String nombreUsuario, @RequestParam(required=false) String email,
//                            @RequestParam(required=false) String nombre, @RequestParam(required=false) String apellido, @RequestParam(required=false) Long dni,
//                            @RequestParam(required=false) Date fechaNacimiento, @RequestParam(required=false) String telefono, @RequestParam(required=false) String direccion,
//                            @RequestParam Long idUsuario){
//        try{
//            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(idUsuario);   
//        }catch(ErrorServicio e){
//            modelo.addAttribute("error", e.getMessage());
//        }        
//        
//        return "index.html";
//    }
}
