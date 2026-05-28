package com.example.demo.services;

import com.example.demo.models.Usuarios;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Le dice a Spring que esta clase contiene la lógica de negocio
public class UsuarioServices {

    @Autowired // Inyección de dependencias: "Traeme el repositorio automáticamente"
    private UsuarioRepository usuarioRepository;

    // 1. Método para obtener todos los usuarios
    public List<Usuarios> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // 2. Método para guardar un usuario (Registro)
    public Usuarios guardarUsuario(Usuarios usuario) {
        // Aquí podrías poner lógica como: "Si el correo no tiene @, no lo guardes"
        return usuarioRepository.save(usuario);
    }

    // 3. LÓGICA DE LOGIN
    public Usuarios login(String correo, String contrasena) {
        // Buscamos al usuario por su correo
        Optional<Usuarios> usuarioOpcional = usuarioRepository.findByCorreo(correo);

        if (usuarioOpcional.isPresent()) {
            Usuarios usuario = usuarioOpcional.get();
            // Comparamos la contraseña (en un proyecto real esto se encriptaría)
            if (usuario.getContrasena().equals(contrasena)) {
                return usuario; // Login exitoso
            }
        }
        return null; // Si algo falla, devolvemos nada
    }
    public boolean existeCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).isPresent();
    }

    public boolean actualizarPassword(String correo, String nuevaPassword) {
        Optional<Usuarios> usuarioOpcional = usuarioRepository.findByCorreo(correo);
        if (usuarioOpcional.isPresent()) {
            Usuarios usuario = usuarioOpcional.get();
            usuario.setContrasena(nuevaPassword);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public Usuarios actualizarPerfil(Integer id, String nombre, String telefono, String ubicacion, String passwordActual, String nuevaPassword) throws Exception {
        Optional<Usuarios> usuarioOpcional = usuarioRepository.findById(id);
        if (usuarioOpcional.isPresent()) {
            Usuarios usuario = usuarioOpcional.get();
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            if (ubicacion != null) {
                usuario.setUbicacion(ubicacion);
            }
            if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
                if (!usuario.getContrasena().equals(passwordActual)) {
                    throw new Exception("La contraseña actual es incorrecta.");
                }
                usuario.setContrasena(nuevaPassword);
            }
            return usuarioRepository.save(usuario);
        }
        throw new Exception("Usuario no encontrado.");
    }
}