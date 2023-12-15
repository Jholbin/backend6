package com.centroinformacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Autor;
import com.centroinformacion.entity.Libro;
import com.centroinformacion.entity.LibroHasAutor;
import com.centroinformacion.entity.LibroHasAutorPK;
import com.centroinformacion.repository.LibroHasAutorRepository;
import com.centroinformacion.repository.LibroRepository;

@Service
public class LibroServiceImp implements LibroService {

    @Autowired
    private LibroRepository repository;
    
    @Autowired
    private LibroHasAutorRepository autorRepository;

    @Override
    public Libro insertaActualizaLibro(Libro obj) {
        return repository.save(obj);
    }

    @Override
    public List<Libro> listaLibro() {
        return repository.findAll();
    }

    @Override
    public List<Libro> listaLibroPorTituloLike(String nombre) {
        return repository.listaPorTituloLike(nombre);
    }

    @Override
    public List<Libro> listaPorSerie(String serie) {
        return repository.listaPorSerie(serie);
    }

    @Override
    public List<Libro> listaPorTitutlo(String titulo) {
        return repository.listaPorTitulo(titulo);
    }

    @Override
    public List<Libro> listaPorTituloPorId(String titulo, Integer id) {
        return repository.listaPorTituloPorId(titulo, id);
    }

    @Override
    public List<Libro> listaPorSeriePorId(String serie, Integer id) {
        return repository.listaPorSeriePorId(serie, id);
    }

    @Override
    public Libro obtenerLibroPorId(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminarLibro(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Libro> listaConsulta(String titulo, String anio, String serie ,int idCategoriaLibro, int idDataCatalogo) {
        return repository.listaConsulta(titulo, anio, serie , idCategoriaLibro,  idDataCatalogo);
    }

	@Override
	public List<Autor> traerAutorDeLibro(int idLibro) {
		return repository.traerAutorDeLibro(idLibro);
	}

	@Override
	public LibroHasAutor insertaAutor(LibroHasAutor obj) {
		return autorRepository.save(obj);
	}

	@Override
	public void eliminaAutor(LibroHasAutor obj) {
		autorRepository.delete(obj);
		
	}

	@Override
	public Optional<LibroHasAutor> buscaAutor(LibroHasAutorPK obj) {
		return autorRepository.findById(obj);
	}

}