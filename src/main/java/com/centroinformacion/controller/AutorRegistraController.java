package com.centroinformacion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Autor;
import com.centroinformacion.service.AutorService;
import com.centroinformacion.util.AppSettings;
import com.centroinformacion.util.Constantes;


@RestController
@RequestMapping("/url/autor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class AutorRegistraController {

	@Autowired
	private AutorService AutorService;
	
	@GetMapping("/listaAutorPorNombreLike/{nom}")
	@ResponseBody
	public ResponseEntity<List<Autor>> listaAutorPorNombreLike(@PathVariable("nom") String nom) {
		List<Autor> lista = null;
		try {
			if(nom.equals("todos")) {
				lista = AutorService.listaAutorPorNombreLike("%");
			}else {
				lista = AutorService.listaAutorPorNombreLike("%"+ nom +"%");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/registraAutor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaAutor(@RequestBody Autor obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setIdAutor(0);
			obj.setFechaRegistro(new Date());
			obj.setFechaActualizacion(new Date());
			obj.setEstado(1);
			
			
			List<Autor> lstAutorTelefono =  AutorService.listaPorTelefonoIgualRegistra(obj.getTelefono());
			if (!lstAutorTelefono.isEmpty()) {
				salida.put("mensaje", "El Telefono " + obj.getTelefono() + " ya existe");
				return ResponseEntity.ok(salida);
			}

			
			Autor objSalida =  AutorService.insertaActualizaAutor(obj);
			if (objSalida == null) {
				salida.put("mensaje", Constantes.MENSAJE_REG_ERROR);
			} else {
				salida.put("mensaje", Constantes.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("/actualizaAutor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaAutor(@RequestBody Autor obj) {
		Map<String, Object> salida = new HashMap<>();	

		obj.setFechaActualizacion(new Date());
		
		
		List<Autor> lstAutorTelefono =  AutorService.listaPorTelefonoIgualActualiza(obj.getTelefono(), obj.getIdAutor());
		if (!lstAutorTelefono.isEmpty()) {
			salida.put("mensaje", "El Telefono  " + obj.getTelefono() + " ya existe");
			return ResponseEntity.ok(salida);
		}

			
		try {
			Autor objSalida =  AutorService.insertaActualizaAutor(obj);
			if (objSalida == null) {
				salida.put("mensaje", Constantes.MENSAJE_ACT_ERROR);
			} else {
				salida.put("mensaje", Constantes.MENSAJE_ACT_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_ACT_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	@DeleteMapping("/eliminaAutor/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaAutor(@PathVariable("id") int idAutor) {
		Map<String, Object> salida = new HashMap<>();
		try {
			AutorService.eliminarAutor(idAutor);
			salida.put("mensaje", Constantes.MENSAJE_ELI_EXITOSO);
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
}
