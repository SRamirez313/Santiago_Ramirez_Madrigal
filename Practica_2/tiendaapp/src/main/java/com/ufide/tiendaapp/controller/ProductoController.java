package com.ufide.tiendaapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufide.tiendaapp.entity.Producto;
import com.ufide.tiendaapp.service.ProductoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listar(Model modelo,
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String categoria) {
        if (buscar != null && !buscar.isBlank()) {
            modelo.addAttribute("productos", productoService.buscarPorNombre(buscar));
            modelo.addAttribute("filtro", "Buscando: " + buscar);
        } else if (categoria != null && !categoria.isBlank()) {
            modelo.addAttribute("productos", productoService.buscarPorCategoria(categoria));
            modelo.addAttribute("filtro", "Categoria: " + categoria);
        } else {
            modelo.addAttribute("productos", productoService.listar());
        }
        return "productos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormNuevo(Model m) {
        m.addAttribute("producto", new Producto());
        return "productos/form";
    }

    @GetMapping("/bajo-stock")
    public String bajoStock(Model modelo) {
        modelo.addAttribute("productos", productoService.bajoStock());
        modelo.addAttribute("filtro", "Productos con bajo stock");
        return "productos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormEditar(@PathVariable Long id, Model m) {
        Producto p = productoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        m.addAttribute("producto", p);
        return "productos/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
            @Valid @ModelAttribute("producto") Producto producto,
            BindingResult result,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "productos/form";
        }
        producto.setId(id);
        productoService.guardar(producto);
        ra.addFlashAttribute("ok", "Producto actualizado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/{id}")
    public String detalle(Model modelo, @PathVariable Long id) {
        modelo.addAttribute("producto", productoService.buscarPorId(id).orElse(null));
        return "producto";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
            BindingResult result,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "productos/form";
        }
        productoService.guardar(producto);
        ra.addFlashAttribute("ok", "Producto guardado correctamente");
        return "redirect:/productos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        productoService.eliminar(id);
        ra.addFlashAttribute("ok", "Producto eliminado correctamente");
        return "redirect:/productos";
    }
}
