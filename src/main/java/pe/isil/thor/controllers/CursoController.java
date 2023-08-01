package pe.isil.thor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.thor.models.Curso;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private RestTemplate restTemplate; //permite usar la plantilla para consumir el api rest

    private static final String GET_ALL_CURSOS_API="http://localhost:9000/cursos";  //URL DEL API REST - SERVIDOR DEL MICRO SERVICIO
    private static final String GET_CURSOS_BY_ID_API = "http://localhost:9000/cursos/{id}";
    private static final String CREATE_CURSO_API = "http://localhost:9000/cursos";
    private static final String UPDATE_CURSO_API = "http://localhost:9000/cursos/{id}";
    private static final String DELETE_CURSO_API = "http://localhost:9000/cursos/{id}";


    @GetMapping()
    public String index(Model model){

        ResponseEntity<Curso[]> cursos = restTemplate.getForEntity(GET_ALL_CURSOS_API,Curso[].class);
        model.addAttribute("cursos",cursos.getBody());
        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("curso",new Curso());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String Crear(Curso curso, Model model, RedirectAttributes ra){
        ResponseEntity<Curso> curso1 = restTemplate.postForEntity(CREATE_CURSO_API,curso,Curso.class);
        ra.addFlashAttribute("msgExito","Curso Creado!");
        return "redirect:/cursos";
    }

    @GetMapping("{id}/editar")
    public String editar(@PathVariable Integer id,Model model){
        //ResponseEntity<Curso> curso = restTemplate.getForEntity(GET_CURSOS_BY_ID_API,Curso.class,id);
        //model.addAttribute("curso",curso.getBody());
        Curso curso = restTemplate.getForObject(GET_CURSOS_BY_ID_API,Curso.class,id);
        model.addAttribute("curso",curso);
        return "editar";
    }

    @PostMapping("{id}/editar")
    public String actualizar(@PathVariable Integer id,Curso curso, Model model, RedirectAttributes ra){
        restTemplate.put(UPDATE_CURSO_API,curso,id);
        ra.addFlashAttribute("msgExito","Curso Actualizado");
        return "redirect:/cursos";
    }

    @PostMapping("{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra){
        restTemplate.delete(UPDATE_CURSO_API,id);
        ra.addFlashAttribute("msgExito","Curso Eliminado");
        return "redirect:/cursos";
    }
}
