package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.Autor;

public interface AutorService {

	public abstract Autor insertaActualizaAutor(Autor obj);

	public abstract List<Autor> listaAutorPorNombreLike(String nombre);
	
	public abstract List<Autor> listaAutor();
	
	public abstract void eliminarAutor(int idAutor);
	
	public abstract List<Autor> listaPorTelefonoIgualRegistra(String telefono);

	public abstract List<Autor> listaPorTelefonoIgualActualiza(String telefono, int idAutor);
	
	public abstract List<Autor> listaConsulta(String nombres, String telefono, int estado, int idPais);
}
