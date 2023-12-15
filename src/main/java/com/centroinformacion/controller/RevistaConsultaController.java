package com.centroinformacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.centroinformacion.entity.Revista;
import com.centroinformacion.service.RevistaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@RestController
@RequestMapping("/url/consultaRevista")
@CrossOrigin(origins = "http://localhost:4200")
@CommonsLog
public class RevistaConsultaController {
	@Autowired
	private RevistaService revistaService;
	
	@ResponseBody
	@GetMapping("/consultaRevistaPorParametros")
	public List<Revista> listaConsultaRevista(
			@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(name = "frecuencia", required = false, defaultValue = "") String frecuencia,
			@RequestParam(name = "estado", required = false, defaultValue = "1") int estado,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais
			){List<Revista> lstSalida = revistaService.listaConsulta("%"+nombre+"%",frecuencia, estado, idPais);
	
	return lstSalida;
	}
	
	@PostMapping("/reporteRevistaPdf")
	public void exportaPDF(
			@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(name = "frecuencia", required = false, defaultValue = "") String frecuencia,
			@RequestParam(name = "estado", required = false, defaultValue = "1") int estado,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
			HttpServletRequest request,
			HttpServletResponse response
			) { try {
				List<Revista> lstSalida = revistaService.listaConsulta("%"+nombre+"%", frecuencia, estado, idPais);
				JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);
				
				String fileReporte = request.getServletContext().getRealPath("/WEB-INF/reportes/ReporteRevista.jasper");
				log.info(">>> fileReporte >>"+ fileReporte);
				
				String fileLogo = request.getServletContext().getRealPath("/WEB-INF/img/logo.jpg");
				log.info(">>> fileLogo >>"+fileLogo);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("RUTA_LOGO",fileLogo);
				
				//PASO4 Se juntas la data, diseño y parámetros
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(new File(fileReporte)));
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		       
				//PASO 5 parametros en el Header del mensajes HTTP
	    		response.setContentType("application/pdf");
	    	    response.addHeader("Content-disposition", "attachment; filename=ReporteRevista.pdf");
			    
				//PASO 6 Se envia el pdf
				OutputStream outStream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			}catch (Exception e) {
				e.printStackTrace();
			}
		
	}

}

