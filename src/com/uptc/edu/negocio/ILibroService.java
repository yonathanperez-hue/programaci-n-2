package com.uptc.edu.negocio;

import java.util.List;
import com.uptc.edu.modelo.Libro;

public interface ILibroService {

    boolean agregarLibro(Libro libro);

    boolean actualizarLibro(String isbn, Libro libroActualizado);

    boolean eliminarLibro(String isbn);

    Libro buscarLibro(String isbn);

    List<Libro> listarLibros();
}	