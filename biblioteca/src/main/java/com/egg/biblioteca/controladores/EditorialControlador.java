package com.egg.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial fue cargado correctamente");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }
        return "index.html";
    }
}
