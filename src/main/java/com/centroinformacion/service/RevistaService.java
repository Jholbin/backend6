package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.Revista;

public interface RevistaService {
	
	public abstract Revista insertaRevista(Revista obj);
	public abstract List<Revista> listaRevistaPorNombreLike(String nombre);
	public abstract List<Revista> listaPorNombreIgualRegistra(String nombre);
	public abstract List<Revista> listaPorNombreRevistaIgualActualiza(String nombre, int idRevista);
	public abstract void eliminarRevista(int idRevista);
	/*CL3*/
	public abstract List<Revista> listaConsulta(String nombre, String frecuencia, int estado, int idPais);
	
}
