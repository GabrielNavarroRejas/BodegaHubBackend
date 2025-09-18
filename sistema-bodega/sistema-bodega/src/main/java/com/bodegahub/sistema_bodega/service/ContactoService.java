package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Contacto;
import com.bodegahub.sistema_bodega.repository.ContactoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public Contacto guardarContacto(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public List<Contacto> listarContactos() {
        return contactoRepository.findAll();
    }
}

