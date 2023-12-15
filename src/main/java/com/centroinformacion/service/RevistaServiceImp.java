package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Revista;
import com.centroinformacion.repository.RevistaRepository;

@Service
public class RevistaServiceImp implements RevistaService {
	
	@Autowired
	private RevistaRepository repository;

	@Override
	public Revista insertaRevista(Revista objRevista) {
		// TODO Auto-generated method stub
		return repository.save(objRevista);
	}

	//ok
	@Override
	public List<Revista> listaRevistaPorNombreLike(String nombre) {
		return repository.listaPorNombreLike(nombre);
	}

	@Override
	public List<Revista> listaPorNombreIgualRegistra(String nombre) {
		return repository.listaPorNombreIgualRegistra(nombre);
	}

	@Override
	public List<Revista> listaPorNombreRevistaIgualActualiza(String nombre, int idRevista) {
		
		return repository.listaPorNombreRevistaIgualActualiza(nombre, idRevista);
	}

	@Override
	public void eliminarRevista(int idRevista) {
		repository.deleteById(idRevista);
		
	}

	@Override
	public List<Revista> listaConsulta(String nombre, String frecuencia, int estado, int idPais) {
		// TODO Auto-generated method stub
		return repository.listaConsulta(nombre, frecuencia, estado, idPais);
	}


	





}
