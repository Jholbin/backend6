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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.DataCatalogo;
import com.centroinformacion.entity.Libro;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.service.LibroService;
import com.centroinformacion.util.AppSettings;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/url/libro")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class LibroRegistraController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Libro>> listarLibros() {
        List<Libro> lista = libroService.listaLibro();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/listaLibroPorTituloLike/{nom}")
    @ResponseBody
    public ResponseEntity<List<Libro>> listaLibroPorNombreLike(@PathVariable("nom") String nom) {
        List<Libro> lista = null;
        try {
            if (nom.equals("todos")) {
                lista = libroService.listaLibroPorTituloLike("%");
            } else {
                lista = libroService.listaLibroPorTituloLike("%" + nom + "%");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> insertarLibro(@RequestBody Libro obj, HttpSession session) {
        HashMap<String, Object> salida = new HashMap<>();
        Date fechaActual = new Date();
        obj.setFechaActualizacion(fechaActual);
        obj.setFechaRegistro(fechaActual);
        obj.setEstado(AppSettings.ACTIVO);
        DataCatalogo objData = new DataCatalogo();
        objData.setIdDataCatalogo(27);
        obj.setEstadoPrestamo(objData);

        List<Libro> lstSerieUnique =  libroService.listaPorSerie(obj.getSerie());
        if (!lstSerieUnique.isEmpty()) {
            salida.put("mensaje", " La serie " + obj.getSerie() + " ya existe en otro libro, se cancela la operación");
            return ResponseEntity.ok(salida);
        }else{
            List<Libro> lstTituloUnique =  libroService.listaPorTitutlo(obj.getTitulo());
            if (!lstTituloUnique.isEmpty()) {
                salida.put("mensaje", " El Título " + obj.getTitulo() + " ya existe en otro libro, se cancela la operación");
                return ResponseEntity.ok(salida);
            }else{
                Libro libroInsertado = libroService.insertaActualizaLibro(obj);
                if (libroInsertado == null) {
                    salida.put("mensaje", "Error en el registro del libro");
                    return ResponseEntity.badRequest().body(salida);
                } else {
                    salida.put("mensaje", "Se registró el libro con el ID ==> " + libroInsertado.getIdLibro());
                    return ResponseEntity.ok(salida);
                }
            }
        }
    }

    @PutMapping("/{idLibro}")
    @ResponseBody
    public ResponseEntity<?> actualizarLibro(@PathVariable("idLibro") int idLibro, @RequestBody Libro obj) {
        HashMap<String, Object> salida = new HashMap<>();
        Libro libroExistente = libroService.obtenerLibroPorId(idLibro);
        if (libroExistente == null) {
            salida.put("mensaje", "Libro no encontrado con ID ==> " + idLibro);
            return ResponseEntity.badRequest().body(salida);
        }
        libroExistente.setTitulo(obj.getTitulo());
        libroExistente.setAnio(obj.getAnio());
        libroExistente.setSerie(obj.getSerie());
        libroExistente.setEstado(obj.getEstado());
        libroExistente.setFechaRegistro(new Date());



        List<Libro> lstSerieUnique =  libroService.listaPorSeriePorId(obj.getSerie(), obj.getIdLibro());
        if (!lstSerieUnique.isEmpty()) {
            salida.put("mensaje", " La serie " + obj.getSerie() + " ya existe en otro libro, se cancela la operación");
            return ResponseEntity.ok(salida);
        }else{
            List<Libro> lstTituloUnique =  libroService.listaPorTituloPorId(obj.getTitulo(), obj.getIdLibro());
            if (!lstTituloUnique.isEmpty()) {
                salida.put("mensaje", " El Título " + obj.getTitulo() + " ya existe en otro libro, se cancela la operación");
                return ResponseEntity.ok(salida);
            }else{
                Libro libroActualizado = libroService.insertaActualizaLibro(libroExistente);
                if (libroActualizado == null) {
                    salida.put("mensaje", "Error en la actualización del libro");
                    return ResponseEntity.badRequest().body(salida);
                } else {
                    salida.put("mensaje", "Libro actualizado con ID ==> " + libroActualizado.getIdLibro());
                    return ResponseEntity.ok(salida);
                }
            }
        }





    }

    @PutMapping("/actualizaLibro")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizaLibro(@RequestBody Libro obj) {
        Map<String, Object> salida = new HashMap<>();
        try {

            obj.setFechaActualizacion(new Date());
            Libro objSalida;

            //Validación de DNI unique


            List<Libro> lstSerieUnique =  libroService.listaPorSeriePorId(obj.getSerie(), obj.getIdLibro());
            if (!lstSerieUnique.isEmpty()) {
                salida.put("mensaje", " La serie " + obj.getSerie() + " ya existe en otro libro, se cancela la operación");
                return ResponseEntity.ok(salida);
            }else{
                List<Libro> lstTituloUnique =  libroService.listaPorTituloPorId(obj.getTitulo(), obj.getIdLibro());
                if (!lstTituloUnique.isEmpty()) {
                    salida.put("mensaje", " El Título " + obj.getTitulo() + " ya existe en otro libro, se cancela la operación");
                    return ResponseEntity.ok(salida);
                }else{
                    objSalida =  libroService.insertaActualizaLibro(obj);
                    if (objSalida == null) {
                        salida.put("mensaje", AppSettings.MENSAJE_ACT_ERROR);
                    } else {
                        salida.put("mensaje", AppSettings.MENSAJE_ACT_EXITOSO);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", AppSettings.MENSAJE_ACT_ERROR);
        }
        return ResponseEntity.ok(salida);
    }

    @DeleteMapping("/eliminaLibro/{idLibro}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> eliminaDocente(@PathVariable("idLibro") int idLibro) {
        Map<String, Object> salida = new HashMap<>();
        try {
            System.out.println("Eliminando libro con ID: " + idLibro);
            libroService.eliminarLibro(idLibro);
            salida.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
        }
        return ResponseEntity.ok(salida);
    }

    @ResponseBody
    @GetMapping("/consultaLibroPorParametros")
    public List<Libro> listaConsultaLibro(
            @RequestParam(name = "titulo", required = false, defaultValue = "") String titulo,
            @RequestParam(name = "anio", required = false, defaultValue = "") String anio,
            @RequestParam(name = "serie", required = false, defaultValue = "") String serie,
            @RequestParam(name = "idCategoriaLibro", required = false, defaultValue = "-1") int idCategoriaLibro,
            @RequestParam(name = "idDataCatalogo", required = false, defaultValue = "-1") int idDataCatalogo) {

        List<Libro> lstSalida = libroService.listaConsulta("%" + titulo + "%", anio, serie, idCategoriaLibro, idDataCatalogo);
        return lstSalida;
    }


}