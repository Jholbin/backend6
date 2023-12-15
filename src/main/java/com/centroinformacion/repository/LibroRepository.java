package com.centroinformacion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.centroinformacion.entity.Libro;
import com.centroinformacion.entity.Autor;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    @Query("SELECT x FROM Libro x WHERE x.titulo LIKE :nombre")
    public List<Libro> listaPorTituloLike(@Param("nombre") String nombre);


    @Query("select x from Libro x where x.serie = ?1")
    public abstract List<Libro> listaPorSerie(String serie);

    @Query("select x from Libro x where x.titulo = ?1")
    public abstract List<Libro> listaPorTitulo(String titulo);

    @Query("select x from Libro x where x.serie = ?1 and x.idLibro != ?2")
    public abstract List<Libro> listaPorSeriePorId(String serie, Integer id);

    @Query("select x from Libro x where x.titulo = ?1 and x.idLibro != ?2")
    public abstract List<Libro> listaPorTituloPorId(String titulo, Integer id);

    @Query("select x from Libro x where (?1 = '' or x.titulo like ?1) and (?2 = '' or x.anio = ?2) and (?3 = '' or x.serie = ?3) and  (?4 = -1 or x.categoriaLibro.idDataCatalogo = ?4) and (?5 = -1 or x.tipoLibro.idDataCatalogo = ?5)")
    public abstract List<Libro> listaConsulta(String titulo, String anio, String serie ,int idCategoriaLibro, int idDataCatalogo);

    @Query("Select r.autor from LibroHasAutor r where r.libro.idLibro = ?1")
	public abstract List<Autor> traerAutorDeLibro(int idLibro);
}
