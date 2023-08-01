package pe.isil.thor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pe.isil.thor.models.Usuario;


@Controller
@RequestMapping("/login")
public class AccesoController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String GET_ACCESO_JWT_API = "http://localhost:7000/acceso";
    private static final String POST_ACCESO_JWT_API = "http://localhost:7000/acceso";
    private static final String GET_SALUDO_JWT_API = "http://localhost:7000/hola";

    @GetMapping()
    public String login(Model model){
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping()
    public String token_acceso(Model model,
                               @RequestParam ("nombreUsuario") String nombreUsuario,
                               @RequestParam ("contrasena") String contrasena){

        //System.out.println(usuario.getNombreUsuario());
       //HttpEntity<Usuario> request1 = new HttpEntity<>(new Usuario());
        //Map<String, String> uriVariables = new HashMap<>();
        //uriVariables.put("nombreUsuario", nombreUsuario);
        //uriVariables.put("contrasena",contrasena);
        Usuario req = new Usuario();
        req.setNombreUsuario(nombreUsuario);
        req.setContrasena(contrasena);

        //Autorizacion
       Usuario usuario = restTemplate.postForObject(POST_ACCESO_JWT_API,req,Usuario.class);

        //Creacion headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", usuario.getToken());

        //Creacion de request
        HttpEntity request = new HttpEntity(httpHeaders);

        //Consumir el API JWT enviando el request
        ResponseEntity<String> response = new RestTemplate().exchange(GET_SALUDO_JWT_API, HttpMethod.GET, request, String.class);

        //Obtener JSON response
        String msje = response.getBody();

        model.addAttribute("msje", msje);
        return "mensaje";
}
}