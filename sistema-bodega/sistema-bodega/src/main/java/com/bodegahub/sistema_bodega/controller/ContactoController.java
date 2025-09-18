package com.bodegahub.sistema_bodega.controller;

import com.bodegahub.sistema_bodega.model.Contacto;
import com.bodegahub.sistema_bodega.service.ContactoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @PostMapping
    public Contacto crearContacto(@RequestBody Contacto contacto) {
        return contactoService.guardarContacto(contacto);
    }

    @GetMapping
    public List<Contacto> obtenerContactos() {
        return contactoService.listarContactos();
    }
}

