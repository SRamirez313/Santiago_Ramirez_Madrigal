package com.ufide.biblioapp.controller;

import com.ufide.biblioapp.entity.Rol;
import com.ufide.biblioapp.entity.Usuario;
import com.ufide.biblioapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Bonus: administracion de usuarios LECTOR, mismo patron de rol que el resto (R3).
@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('BIBLIOTECARIO')")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarLectores());
        return "usuarios";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        Usuario usuario = new Usuario();
        usuario.setRol(Rol.LECTOR.name());
        model.addAttribute("usuario", usuario);
        return "usuario-form";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null || !usuario.getRol().equals(Rol.LECTOR.name())) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuario-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Usuario usuario) {
        usuario.setRol(Rol.LECTOR.name()); // esta pantalla solo administra lectores
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null && usuario.getRol().equals(Rol.LECTOR.name())) {
            usuarioService.eliminar(id);
        }
        return "redirect:/usuarios";
    }
}