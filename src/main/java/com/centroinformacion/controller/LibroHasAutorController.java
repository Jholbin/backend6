package com.centroinformacion.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Autor;
import com.centroinformacion.entity.LibroHasAutor;
import com.centroinformacion.entity.LibroHasAutorPK;
import com.centroinformacion.service.LibroService;


import lombok.extern.apachecommons.CommonsLog;


@RestController
@RequestMapping("/url/asignacionLibro")
@CrossOrigin(origins = "http://localhost:4200")
@CommonsLog
public class LibroHasAutorController {
	
	@Autowired 
	private LibroService libroService;
	
	@ResponseBody
	@GetMapping("/listaAutorPorLibro/{id}")
	public List<Autor> listaAutorPorLibro(@PathVariable("id")int idLibro){
		return  libroService.traerAutorDeLibro(idLibro);
	}

	@ResponseBody
	@GetMapping("/registraAutor")
	public HashMap<String, Object> registro(
			@RequestParam(name = "idLibro" , defaultValue = "-1" , required = true)int idLibro, 
			@RequestParam(name = "idAutor" , defaultValue = "-1" , required = true)int idAutor){
		HashMap<String, Object> maps = new HashMap<String, Object>();
		
		LibroHasAutorPK pk = new LibroHasAutorPK();
		pk.setIdAutor(idAutor);
		pk.setIdLibro(idLibro);

		LibroHasAutor obj = new LibroHasAutor();
		obj.setLibroHasAutorPK(pk);
		
		Optional<LibroHasAutor> existentOpcion = libroService.buscaAutor(pk);
        if (existentOpcion.isEmpty()) {
        	LibroHasAutor objSalida = libroService.insertaAutor(obj);
        	if (objSalida == null) {
        		maps.put("mensaje", "Error en el registro");		
        	}else {
        		maps.put("mensaje", "Registro exitoso");
        	}
        }else {
        	maps.put("mensaje", "Ya existe el pasatiempo");
        }
        List<Autor> lstOpcion =  libroService.traerAutorDeLibro(idLibro);
        maps.put("lista", lstOpcion);
		return maps;
	}
	
	@ResponseBody
	@GetMapping("/eliminaAutor")
	public HashMap<String, Object> elimina(
			@RequestParam(name = "idLibro" , defaultValue = "-1" , required = true)int idLibro, 
			@RequestParam(name = "idAutor" , defaultValue = "-1" , required = true)int idAutor){
		HashMap<String, Object> maps = new HashMap<String, Object>();
		
		LibroHasAutorPK pk = new LibroHasAutorPK();
		pk.setIdAutor(idAutor);
		pk.setIdLibro(idLibro);

		LibroHasAutor obj = new LibroHasAutor();
		obj.setLibroHasAutorPK(pk);
		
		libroService.eliminaAutor(obj);
		maps.put("mensaje", "Eliminación exitosa");
		
		List<Autor> lstOpcion =  libroService.traerAutorDeLibro(idLibro);
        maps.put("lista", lstOpcion);

		return maps;
	}
	
	
	

}
