package com.centroinformacion.service;

import java.util.List;
import java.util.Optional;

import com.centroinformacion.entity.Autor;
import com.centroinformacion.entity.Libro;
import com.centroinformacion.entity.LibroHasAutor;
import com.centroinformacion.entity.LibroHasAutorPK;

public interface LibroService {

    public abstract Libro insertaActualizaLibro(Libro obj);
    public abstract List<Libro> listaLibro();
    public abstract List<Libro> listaLibroPorTituloLike(String nombre);
    public abstract List<Libro> listaPorSerie(String serie);
    public abstract List<Libro> listaPorTitutlo(String titulo);
    public abstract List<Libro> listaPorSeriePorId(String serie, Integer id);
    public abstract List<Libro> listaPorTituloPorId(String serie, Integer id);
    public abstract Libro obtenerLibroPorId(int id);
    public abstract void eliminarLibro(int id);
    public abstract List<Libro> listaConsulta(String titulo, String anio, String serie ,int idCategoriaLibro, int idDataCatalogo);

    public abstract List<Autor> traerAutorDeLibro(int idLibro);
    public abstract LibroHasAutor insertaAutor(LibroHasAutor obj);
	public abstract void eliminaAutor(LibroHasAutor obj);
	public abstract Optional<LibroHasAutor> buscaAutor(LibroHasAutorPK obj);
    
}
