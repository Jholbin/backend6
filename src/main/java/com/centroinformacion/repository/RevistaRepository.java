package com.centroinformacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Revista;

public interface RevistaRepository extends JpaRepository<Revista, Integer> {

	@Query("select x from Revista x where x.nombre like ?1")
	public abstract List<Revista> listaPorNombreLike(String nombre);

	// Validaciones CRUD Registrar
	@Query("select x from Revista x where x.nombre = ?1")
	public abstract List<Revista> listaPorNombreIgualRegistra(String nombre);

	// Validaciones CRUD Actualizar

	@Query("select x from Revista x where x.nombre = ?1 and x.idRevista != ?2")
	public abstract List<Revista> listaPorNombreRevistaIgualActualiza(String nombre, int idRevista);

	/* CL3 */
	@Query("select x from Revista x where (x.nombre like ?1) and (?2 = '' or x.frecuencia = ?2) and (x.estado = ?3 ) and (?4 = -1 or x.pais.idPais = ?4)")
	public abstract List<Revista> listaConsulta(String nombre, String frecuencia, int estado, int idPais);
}
