package com.centroinformacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer>{
		
	@Query("select x from Autor x where x.nombres like ?1")
	public abstract List<Autor> listaPorNombreLike(String nombre);
	
	//Validaciones CRUD Registrar
	
	@Query("select x from Autor x where x.telefono = ?1")
	public abstract List<Autor> listaPorTelefonoIgualRegistra(String telefono);
	
	
	//Validaciones CRUD Actualizar
	
	@Query("select x from Autor x where x.telefono = ?1 and x.idAutor != ?2")
	public abstract List<Autor> listaPorTelefonoAutorIgualActualiza(String nombre, int idAutor);
	
	//p03
	
	@Query("select x from Autor x where (x.nombres like ?1) and (?2 = '' or x.telefono = ?2) and (x.estado = ?3 ) and (?4 = -1 or x.pais.idPais = ?4)")
	public abstract List<Autor> listaConsulta(String nombres, String telefono, int estado, int idPais);

}
