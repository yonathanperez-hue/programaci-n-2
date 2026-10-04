package com.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.List;

import com.uptc.edu.modelo.Libro;

public class LibroService implements ILibroService {

    private List<Libro> libros;

    public LibroService() {
        libros = new ArrayList<>();
    }

    @Override
    public boolean agregarLibro(Libro libro) {

        if (buscarLibro(libro.getIsbn()) != null) {
            return false;
        }

        return libros.add(libro);
    }

    @Override
    public boolean actualizarLibro(String isbn, Libro libroActualizado) {

        Libro libro = buscarLibro(isbn);

        if (libro != null) {

            libro.setTitulo(libroActualizado.getTitulo());
            libro.setAutor(libroActualizado.getAutor());
            libro.setAnioPublicacion(libroActualizado.getAnioPublicacion());
            libro.setCategoria(libroActualizado.getCategoria());
            libro.setEditorial(libroActualizado.getEditorial());
            libro.setNumeroPaginas(libroActualizado.getNumeroPaginas());
            libro.setPrecio(libroActualizado.getPrecio());
            libro.setCantidadDisponible(libroActualizado.getCantidadDisponible());
            libro.setFormato(libroActualizado.getFormato());

            return true;
        }

        return false;
    }

    @Override
    public boolean eliminarLibro(String isbn) {

        Libro libro = buscarLibro(isbn);

        if (libro != null) {
            return libros.remove(libro);
        }

        return false;
    }

    @Override
    public Libro buscarLibro(String isbn) {

        for (Libro libro : libros) {

            if (libro.getIsbn().equalsIgnoreCase(isbn)) {
                return libro;
            }
        }

        return null;
    }

    @Override
    public List<Libro> listarLibros() {
        return libros;
    }
}