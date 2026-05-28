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

    @Autowired
    private com.example.demo.services.EntregaServices entregaService;

    // ── LOGIN ──────────────────────────────────────────
    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/usuarios/dashboard"; // Ya autenticado, ir al panel correspondiente
        }
        return "usuarios/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
            @RequestParam String contrasena,
            HttpSession session,
            Model model) {

        Usuarios usuarioEncontrado = usuarioService.login(correo, contrasena);

        if (usuarioEncontrado != null) {
            // Guardar usuario en sesión y mantener la sesión activa
            session.setAttribute("usuario", usuarioEncontrado);
            session.setAttribute("cantidadCarrito", 0);
            session.setMaxInactiveInterval(1800); // 30 minutos

            return redirigirPorRol(usuarioEncontrado);
        } else {
            model.addAttribute("errorLogin", "Correo o contraseña incorrectos");
            return "usuarios/login";
        }
    }

    // ── REGISTRO ───────────────────────────────────────
    @GetMapping("/registro")
    public String mostrarRegistro(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/usuarios/dashboard"; // Ya autenticado, no permitir registrar nuevamente
        }
        return "usuarios/registro";
    }

    @PostMapping("/registro")
    public String guardarRegistro(@ModelAttribute Usuarios usuario,
            HttpSession session,
            Model model) {
        // Verificar si el correo ya existe
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("errorRegistro", "Ya existe una cuenta con ese correo");
            return "usuarios/registro";
        }
        if (!esRolValido(usuario.getRol())) {
            model.addAttribute("errorRegistro", "Selecciona un tipo de usuario válido");
            return "usuarios/registro";
        }

        Usuarios usuarioGuardado = usuarioService.guardarUsuario(usuario);

        // Login automático después del registro
        session.setAttribute("usuario", usuarioGuardado);
        session.setAttribute("cantidadCarrito", 0);
        session.setMaxInactiveInterval(1800); // 30 minutos

        return redirigirPorRol(usuarioGuardado);
    }

    // ── DASHBOARDS ──────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboardRedirect(HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        return redirigirPorRol(usuario);
    }

    @GetMapping("/dashboard-campesino")
    public String dashboardCampesino(HttpSession session, Model model) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        if (!"CAMPESINO".equalsIgnoreCase(usuario.getRol())) {
            return "redirect:/usuarios/dashboard";
        }

        model.addAttribute("misProductos", productoService.listarPorUsuario(usuario.getId_usuario()));
        try {
            model.addAttribute("entregas", entregaService.listarEntregas());
        } catch (Exception e) {
        }

        return "usuarios/dashboard-campesino";
    }

    @GetMapping("/dashboard-cliente")
    public String dashboardCliente(HttpSession session, Model model) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        if (!"CLIENTE".equalsIgnoreCase(usuario.getRol())) {
            return "redirect:/usuarios/dashboard";
        }

        return "usuarios/dashboard-cliente";
    }

    // ── RECUPERAR CONTRASEÑA ───────────────────────────
    @GetMapping("/recuperar-password")
    public String mostrarRecuperarPassword() {
        return "usuarios/recuperar-password";
    }

    @PostMapping("/recuperar-password")
    public String procesarRecuperarPassword(@RequestParam String correo,
            @RequestParam String nuevaPassword,
            Model model) {
        boolean exito = usuarioService.actualizarPassword(correo, nuevaPassword);
        if (exito) {
            model.addAttribute("exito", "Contraseña actualizada exitosamente. Ahora puedes iniciar sesión.");
        } else {
            model.addAttribute("error", "No se encontró un usuario con ese correo.");
        }
        return "usuarios/recuperar-password";
    }

    // ── PERFIL ─────────────────────────────────────────
    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        return "usuarios/perfil";
    }

    @PostMapping("/perfil")
    public String procesarPerfil(@RequestParam String nombre,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) String passwordActual,
            @RequestParam(required = false) String nuevaPassword,
            HttpSession session,
            Model model) {
        Usuarios usuarioLogueado = (Usuarios) session.getAttribute("usuario");
        if (usuarioLogueado == null) {
            return "redirect:/usuarios/login";
        }

        try {
            Usuarios usuarioActualizado = usuarioService.actualizarPerfil(
                    usuarioLogueado.getId_usuario(), nombre, telefono, ubicacion, passwordActual, nuevaPassword);

            session.setAttribute("usuario", usuarioActualizado);
            model.addAttribute("mensaje", "Perfil actualizado correctamente.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "usuarios/perfil";
    }

    // ── LOGOUT ─────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * Helper para redirigir al dashboard correcto según el rol del usuario.
     */
    private String redirigirPorRol(Usuarios usuario) {
        if (usuario == null || usuario.getRol() == null) {
            return "redirect:/usuarios/login";
        }
        if ("CAMPESINO".equalsIgnoreCase(usuario.getRol())) {
            return "redirect:/usuarios/dashboard-campesino";
        }
        if ("CLIENTE".equalsIgnoreCase(usuario.getRol())) {
            return "redirect:/usuarios/dashboard-cliente";
        }
        return "redirect:/usuarios/login";
    }

    private boolean esRolValido(String rol) {
        return rol != null && ("CAMPESINO".equalsIgnoreCase(rol) || "CLIENTE".equalsIgnoreCase(rol));
    }
}