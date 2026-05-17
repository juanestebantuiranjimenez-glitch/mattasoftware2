package com.example.demo.repositories;

import com.example.demo.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.id_usuario = :idUsuario")
    List<Producto> findByIdUsuario(@Param("idUsuario") int idUsuario);
}