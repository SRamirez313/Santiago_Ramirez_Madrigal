package com.ufide.biblioapp.service;

import com.ufide.biblioapp.entity.Libro;
import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.entity.Usuario;
import com.ufide.biblioapp.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroService libroService;

    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }

    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    public List<Prestamo> listarPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario);
    }

    public List<Prestamo> prestamosAtrasados() {
        return prestamoRepository.prestamosAtrasados();
    }

    // R2: registrar un préstamo nuevo.
    // - fechaLimite = fechaPrestamo + 14 días.
    // - descuenta una copia disponible del libro.
    public Prestamo registrarPrestamo(Libro libro, Usuario usuario) {
        if (libro.getCopiasDisponibles() == null || libro.getCopiasDisponibles() <= 0) {
            throw new IllegalStateException("No hay copias disponibles de este libro");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaLimite(LocalDate.now().plusDays(14));
        prestamo.setFechaDevolucion(null);

        libroService.descontarCopia(libro);

        return prestamoRepository.save(prestamo);
    }

    // R2: registrar la devolución de un préstamo.
    // - marca fechaDevolucion = hoy.
    // - devuelve la copia al libro.
    public Prestamo registrarDevolucion(Prestamo prestamo) {
        if (prestamo.getFechaDevolucion() != null) {
            throw new IllegalStateException("Este préstamo ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());
        libroService.sumarCopia(prestamo.getLibro());

        return prestamoRepository.save(prestamo);
    }
}