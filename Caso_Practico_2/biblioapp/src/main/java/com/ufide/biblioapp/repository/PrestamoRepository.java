package com.ufide.biblioapp.repository;

import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // R3 - pista del enunciado: Spring Data JPA lo implementa solo
    // a partir del nombre del método, sin @Query.
    List<Prestamo> findByUsuario(Usuario usuario);

    // R5 - Ejemplo 3 (obligatorio): préstamos atrasados
    // No devuelto (fechaDevolucion IS NULL) y fechaLimite ya pasó.
    @Query("SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NULL AND p.fechaLimite < CURRENT_DATE")
    List<Prestamo> prestamosAtrasados();
}
