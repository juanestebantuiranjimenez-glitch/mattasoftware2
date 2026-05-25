package com.example.demo.controllers;

import com.example.demo.models.Producto;
import com.example.demo.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // CAMBIO: Ahora es @Controller
import org.springframework.ui.Model; // Para pasar datos al HTML
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller // CAMBIO: Quitamos el @RestController para poder usar HTML
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoServices productoService;

    @GetMapping({"", "/", "/listar"})
    public String listar(Model model, jakarta.servlet.http.HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/usuarios/login";
        }
        List<Producto> productos = productoService.listarTodos();
        
        // "productos" es el nombre que usamos en el HTML (th:each)
        model.addAttribute("productos", productos);
        
        return "productos/lista"; // Esto busca el archivo templates/productos/lista.html
    }

    // Muestra el formulario para subir producto
    @GetMapping("/nuevo")
    public String mostrarFormulario(jakarta.servlet.http.HttpSession session) {
        com.example.demo.models.Usuarios usuario = (com.example.demo.models.Usuarios) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/usuarios/login";
        if (!"CAMPESINO".equalsIgnoreCase(usuario.getRol())) return "redirect:/productos/listar";

       return "productos/formulario";
    }

    // Guarda el producto en la base de datos
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, 
                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                  jakarta.servlet.http.HttpSession session) {
        
        com.example.demo.models.Usuarios usuario = (com.example.demo.models.Usuarios) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/usuarios/login";
        if (!"CAMPESINO".equalsIgnoreCase(usuario.getRol())) return "redirect:/productos/listar";

        if (file != null && !file.isEmpty()) {
            try {
                // Directorio donde se guardarán las imágenes
                String uploadDir = "src/main/resources/static/uploads/";
                Path uploadPath = Paths.get(uploadDir);
                
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Generar nombre único para la imagen
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                // Asignar el nombre del archivo al producto
                producto.setImagen(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        productoService.guardar(producto);

        return "redirect:/productos/listar"; // Después de guardar, vuelve a la tienda
    }

    // Muestra los detalles de un producto específico
    @GetMapping("/{id}")
    public String verDetalle(@org.springframework.web.bind.annotation.PathVariable("id") int id, Model model, jakarta.servlet.http.HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/usuarios/login";
        }
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            return "redirect:/productos/listar";
        }
        model.addAttribute("producto", producto);
        return "productos/detalle";
    }

    // Muestra formulario de editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@org.springframework.web.bind.annotation.PathVariable("id") int id, Model model, jakarta.servlet.http.HttpSession session) {
        com.example.demo.models.Usuarios usuario = (com.example.demo.models.Usuarios) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/usuarios/login";
        if (!"CAMPESINO".equalsIgnoreCase(usuario.getRol())) return "redirect:/productos/listar";

        Producto producto = productoService.buscarPorId(id);
        if (producto == null) return "redirect:/usuarios/dashboard-campesino";
        model.addAttribute("producto", producto);
        return "productos/formulario-editar";
    }

    // Actualiza el producto
    @PostMapping("/actualizar")
    public String actualizarProducto(@ModelAttribute Producto producto,
                                     @RequestParam(value = "file", required = false) MultipartFile file,
                                     jakarta.servlet.http.HttpSession session) {
        com.example.demo.models.Usuarios usuario = (com.example.demo.models.Usuarios) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/usuarios/login";
        if (!"CAMPESINO".equalsIgnoreCase(usuario.getRol())) return "redirect:/productos/listar";
        
        Producto prodExistente = productoService.buscarPorId(producto.getId_producto());
        if (prodExistente != null) {
            prodExistente.setNombre(producto.getNombre());
            prodExistente.setDescripcion(producto.getDescripcion());
            prodExistente.setPrecio(producto.getPrecio());
            prodExistente.setCantidad_disponible(producto.getCantidad_disponible());
            
            if (file != null && !file.isEmpty()) {
                try {
                    String uploadDir = "src/main/resources/static/uploads/";
                    Path uploadPath = Paths.get(uploadDir);
                    if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    prodExistente.setImagen(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            productoService.guardar(prodExistente);
        }
        return "redirect:/usuarios/dashboard-campesino";
    }

    // Elimina el producto
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@org.springframework.web.bind.annotation.PathVariable("id") int id, jakarta.servlet.http.HttpSession session) {
        com.example.demo.models.Usuarios usuario = (com.example.demo.models.Usuarios) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/usuarios/login";
        if (!"CAMPESINO".equalsIgnoreCase(usuario.getRol())) return "redirect:/productos/listar";

        productoService.eliminar(id);
        return "redirect:/usuarios/dashboard-campesino";
    }
}