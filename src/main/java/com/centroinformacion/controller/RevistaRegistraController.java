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

import com.centroinformacion.entity.Revista;
import com.centroinformacion.service.RevistaService;
import com.centroinformacion.util.AppSettings;
import com.centroinformacion.util.Constantes;


@RestController
@RequestMapping("/url/revista")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class RevistaRegistraController {
	
	@Autowired
	private RevistaService revistaService;
	
	@GetMapping("/listaRevistaPorNombreLike/{nom}")
	@ResponseBody
	public ResponseEntity<List<Revista>> listaRevistaPorNombreLike(@PathVariable("nom") String nom){
		List<Revista> lista = null;
		try {
			if(nom.equals("todos")) {
				lista = revistaService.listaRevistaPorNombreLike("%");
			}else {
				lista = revistaService.listaRevistaPorNombreLike("%"+ nom +"%");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/registraRevista")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaRevista(@RequestBody Revista objRevista){
		Map<String, Object> salida = new HashMap<>();
		
		try {
			objRevista.setIdRevista(0);
			objRevista.setFechaRegistro(new Date());
			objRevista.setFechaActualizacion(new Date());
			objRevista.setEstado(1);
			//objRevista.setEstado(AppSettings.ACTIVO);
			
			List<Revista> lstRevistaNombre = revistaService.listaPorNombreIgualRegistra(objRevista.getNombre());
			if (!lstRevistaNombre.isEmpty()) {
				salida.put("mensaje", "El nombre " + objRevista.getNombre() + " ya existe");
				return ResponseEntity.ok(salida);
			}

			
			Revista objSalida =  revistaService.insertaRevista(objRevista);
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
	
	
	@PutMapping("/actualizaRevista")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaRevista(@RequestBody Revista objRevista) {
		Map<String, Object> salida = new HashMap<>();	

		objRevista.setFechaActualizacion(new Date());
		
		
		List<Revista> lstRevistaNombre =  revistaService.listaPorNombreRevistaIgualActualiza(objRevista.getNombre(), objRevista.getIdRevista());
		if (!lstRevistaNombre.isEmpty()) {
			salida.put("mensaje", "El nombre de la revista  " + objRevista.getNombre() + " ya existe");
			return ResponseEntity.ok(salida);
		}

			
		try {
			Revista objSalida =  revistaService.insertaRevista(objRevista);
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
	
	@DeleteMapping("/eliminaRevista/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaRevista(@PathVariable("id") int idRevista) {
		Map<String, Object> salida = new HashMap<>();
		try {
			revistaService.eliminarRevista(idRevista);
			salida.put("mensaje", Constantes.MENSAJE_ELI_EXITOSO);
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	

	
	

}
