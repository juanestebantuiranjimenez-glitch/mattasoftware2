package com.example.demo.controllers;

import com.example.demo.models.Usuarios;
import com.example.demo.services.UsuarioServices;
import com.example.demo.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioService;

    @Autowired
    private ProductoServices productoService;

    // ── LOGIN ──────────────────────────────────────────
    @GetMapping("/login")
    public String mostrarLogin() {
        return "usuarios/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasena,
                                HttpSession session,
                                Model model) {

        Usuarios usuarioEncontrado = usuarioService.login(correo, contrasena);

        if (usuarioEncontrado != null) {
            // Guardar usuario en sesión
            session.setAttribute("usuario", usuarioEncontrado);
            session.setAttribute("cantidadCarrito", 0);

            // Si es campesino, va al dashboard; si es cliente, va a productos
            if ("CAMPESINO".equalsIgnoreCase(usuarioEncontrado.getTipo_usuario())) {
                return "redirect:/usuarios/dashboard";
            }
            return "redirect:/productos/listar";

        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "usuarios/login";
        }
    }

    // ── REGISTRO ───────────────────────────────────────
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "usuarios/registro";
    }

    @PostMapping("/registro")
    public String guardarRegistro(@ModelAttribute Usuarios usuario,
                                  HttpSession session,
                                  Model model) {
        // Verificar si el correo ya existe
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "Ya existe una cuenta con ese correo");
            return "usuarios/registro";
        }

        usuarioService.guardarUsuario(usuario);

        // Login automático después del registro
        session.setAttribute("usuario", usuario);
        session.setAttribute("cantidadCarrito", 0);

        if ("CAMPESINO".equalsIgnoreCase(usuario.getTipo_usuario())) {
            return "redirect:/usuarios/dashboard";
        }
        return "redirect:/productos/listar";
    }

    // ── DASHBOARD ──────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        // Si no está logueado, mandarlo al login
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }

        // Pasar datos al dashboard
        model.addAttribute("misProductos",
            productoService.listarPorUsuario(usuario.getId_usuario()));

        return "usuarios/dashboard";
    }

    // ── LOGOUT ─────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}