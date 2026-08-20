package com.ufide.biblioapp.controller;

import com.ufide.biblioapp.entity.Libro;
import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.entity.Usuario;
import com.ufide.biblioapp.service.LibroService;
import com.ufide.biblioapp.service.PrestamoService;
import com.ufide.biblioapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private LibroService libroService;

    @Autowired
    private UsuarioService usuarioService;

    
    @GetMapping("/prestamos")
    public String listar(Model model, Authentication auth) {
        boolean esBibliotecario = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_BIBLIOTECARIO"));

        List<Prestamo> prestamos;
        if (esBibliotecario) {
            prestamos = prestamoService.listar();
        } else {
            Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
            prestamos = prestamoService.listarPorUsuario(usuario);
        }

        model.addAttribute("prestamos", prestamos);
        model.addAttribute("esBibliotecario", esBibliotecario);
        return "prestamos";
    }

    
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    @GetMapping("/prestamos/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("libros", libroService.listar());
        model.addAttribute("usuarios", usuarioService.listarLectores());
        return "prestamo-form";
    }

    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    @PostMapping("/prestamos")
    public String registrar(@RequestParam Long libroId, @RequestParam Long usuarioId) {
        Libro libro = libroService.buscarPorId(libroId).orElseThrow();
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        prestamoService.registrarPrestamo(libro, usuario);
        return "redirect:/prestamos";
    }

    
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    @PostMapping("/prestamos/{id}/devolver")
    public String devolver(@PathVariable Long id) {
        Prestamo prestamo = prestamoService.buscarPorId(id).orElseThrow();
        prestamoService.registrarDevolucion(prestamo);
        return "redirect:/prestamos";
    }
}