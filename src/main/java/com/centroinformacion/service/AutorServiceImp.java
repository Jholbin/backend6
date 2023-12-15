package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Autor;
import com.centroinformacion.repository.AutorRepository;

@Service
public class AutorServiceImp implements AutorService {

	@Autowired	
	private AutorRepository repository;

	@Override
	public Autor insertaActualizaAutor(Autor obj) {
		return repository.save(obj);
	}
	

	@Override
	public List<Autor> listaAutorPorNombreLike(String nombre) {
		
		return repository.listaPorNombreLike(nombre);
	}

	@Override
	public void eliminarAutor(int idAutor) {
		repository.deleteById(idAutor);
		
	}


	@Override
	public List<Autor> listaPorTelefonoIgualRegistra(String telefono) {
		return repository.listaPorTelefonoIgualRegistra(telefono);
	}


	@Override
	public List<Autor> listaPorTelefonoIgualActualiza(String telefono, int idAutor) {
		return repository.listaPorTelefonoAutorIgualActualiza(telefono, idAutor);
	}


	@Override
	public List<Autor> listaConsulta(String nombres, String telefono, int estado, int idPais) {
		return repository.listaConsulta(nombres, telefono,estado, idPais);
	}


	@Override
	public List<Autor> listaAutor() {
		
		return repository.findAll();
	}
}
