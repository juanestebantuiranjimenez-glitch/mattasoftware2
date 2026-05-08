package com.example.demo.controllers;

import com.example.demo.models.Usuarios;
import com.example.demo.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // CAMBIO: @Controller
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // CAMBIO: Quitamos @RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioService;

    // 1. Muestra la página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "usuarios/login";
    }

    // 2. Procesa los datos del formulario
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, 
                               @RequestParam String contrasena, 
                               Model model) {
        
        Usuarios usuarioEncontrado = usuarioService.login(correo, contrasena);

        if (usuarioEncontrado != null) {
            // Si el login es correcto, lo mandamos a la lista de productos
            return "redirect:/productos/listar";
        } else {
            // Si falla, volvemos al login con un mensaje de error
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "usuarios/login";
        }
    }
}