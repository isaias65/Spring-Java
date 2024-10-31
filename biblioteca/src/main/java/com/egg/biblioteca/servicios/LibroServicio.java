package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;

@Service
public class LibroServicio {

    @Autowired //variable se inicialize - injeccion de dependencias
    LibroRepositorio libroRepositorio;

    @Autowired //variable se inicialize - injeccion de dependencias
    AutorRepositorio autorRepositorio;

    @Autowired //variable se inicialize - injeccion de dependencias
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, int ejemplares, String idAutor, UUID idEditorial) throws MiException{
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList<>();

        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, int ejemplares, String idAutor, UUID idEditorial) throws MiException{
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }

        if (respuestaLibro.isPresent()) {
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
        }
    }

    private void validar(Long isbn, String titulo, int ejemplares, String idAutor, UUID idEditorial) throws MiException {
        if (isbn == null) {
            throw new MiException("el isbn no puede ser nulo o estar vacio");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }

        if (ejemplares <= 0) {
            throw new MiException("ejemplares no puede ser nulo");
        }
        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("el Autor no puede ser nulo o estar vacio");
        }

        if (idEditorial == null) {
            throw new MiException("La Editorial no puede ser nula o estar vacia");
        }
    }

}
