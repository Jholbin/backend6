package com.centroinformacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
import com.centroinformacion.util.AppSettings;
import com.centroinformacion.util.UtilExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Libro;
import com.centroinformacion.service.LibroService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
/*
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/

@RestController
@RequestMapping("/url/consultaLibro")
@CrossOrigin(origins = "http://localhost:4200")
@CommonsLog
public class LibroConsultaController {

    @Autowired
    private LibroService docenteService;

    @ResponseBody
    @GetMapping("/consultaLibroPorParametros")

    public List<Libro> listaConsultaDocente(
            @RequestParam(name = "titulo" , required = false , defaultValue = "") String titulo,
            @RequestParam(name = "anio" , required = false , defaultValue = "") String anio,
            @RequestParam(name = "serie" , required = false , defaultValue = "") String serie,
            @RequestParam(name = "idCategoriaLibro" , required = false , defaultValue = "-1") int idCategoriaLibro,
            @RequestParam(name = "idDataCatalogo" , required = false , defaultValue = "-1") int idDataCatalogo)
    {
        List<Libro> lstSalida = docenteService.listaConsulta("%"+titulo+"%", anio, serie, idCategoriaLibro,idDataCatalogo );
        return lstSalida;
    }

    @PostMapping("/reporteLibroPdf")
    public void  exportaPDF(
            @RequestParam(name = "titulo" , required = false , defaultValue = "") String titulo,
            @RequestParam(name = "anio" , required = false , defaultValue = "") String anio,
            @RequestParam(name = "serie" , required = false , defaultValue = "") String serie,
            @RequestParam(name = "idCategoriaLibro" , required = false , defaultValue = "-1") int idCategoriaLibro,
            @RequestParam(name = "idDataCatalogo" , required = false , defaultValue = "-1") int idDataCatalogo,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {


            List<Libro> lstSalida = docenteService.listaConsulta("%"+titulo+"%", anio, serie, idCategoriaLibro, idDataCatalogo );
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);

            String fileReporte  = request.getServletContext().getRealPath("/WEB-INF/reportes/reporteLibro.jasper");

            log.info(">>> fileReporte >> " + fileReporte);

            String fileLogo  = request.getServletContext().getRealPath("/WEB-INF/img/logo.jpg");
            log.info(">>> fileLogo >> " + fileLogo);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("RUTA_LOGO", fileLogo);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(new File(fileReporte)));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            response.setContentType("application/x-pdf");
            response.addHeader("Content-disposition", "attachment; filename=ReporteLibro.pdf");

            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

/*
    private static String[] HEADERs = {"Id", "Titulo", "Año de Publicación", "Serie", "Categoría", "Tipo"};
    private static String SHEET = "Listado";
    private static String TITLE = "Listado de libros - Autor: Carlos Cortez ";
    private static int[] HEADER_WITH = {3000, 10000, 6000, 10000, 8000, 8000};

    @PostMapping("/reporteLibroExcel")
    public void  exportaExcel(
            @RequestParam(name = "titulo" , required = false , defaultValue = "") String titulo,
            @RequestParam(name = "anio" , required = false , defaultValue = "") String anio,
            @RequestParam(name = "serie" , required = false , defaultValue = "") String serie,
            @RequestParam(name = "idCategoriaLibro" , required = false , defaultValue = "-1") int idCategoriaLibro,
            @RequestParam(name = "idDataCatalogo" , required = false , defaultValue = "-1") int idDataCatalogo,
            HttpServletRequest request,
            HttpServletResponse response) {

        Workbook excel  = null;
        try {
            //Se crear el Excel
            excel = new XSSFWorkbook();

            //Se crear la hoja del Excel
            Sheet hoja = excel.createSheet(SHEET);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADER_WITH.length - 1));

            //Se establece el ancho de las columnas
            for (int i = 0; i < HEADER_WITH.length; i++) {
                hoja.setColumnWidth(i, HEADER_WITH[i]);
            }

            CellStyle estiloHeadCentrado = UtilExcel.setEstiloHeadCentrado(excel);
            CellStyle estiloHeadIzquierda = UtilExcel.setEstiloHeadIzquierda(excel);
            CellStyle estiloNormalCentrado = UtilExcel.setEstiloNormalCentrado(excel);
            CellStyle estiloNormalIzquierda = UtilExcel.setEstiloNormalIzquierdo(excel);


            //Fila 0
            Row fila1 = hoja.createRow(0);
            Cell celAuxs = fila1.createCell(0);
            celAuxs.setCellStyle(estiloHeadIzquierda);
            celAuxs.setCellValue(TITLE);

            //Fila 1
            Row fila2 = hoja.createRow(1);
            Cell celAuxs2 = fila2.createCell(0);
            celAuxs2.setCellValue("");

            //Fila 2
            Row fila3 = hoja.createRow(2);
            for (int i = 0; i < HEADERs.length; i++) {
                Cell celda1 = fila3.createCell(i);
                celda1.setCellStyle(estiloHeadCentrado);
                celda1.setCellValue(HEADERs[i]);
            }

            List<Libro> lstSalida = docenteService.listaConsulta("%"+titulo+"%", anio, serie, idCategoriaLibro, idDataCatalogo );

            int rowIdx = 3;
            for (Libro obj : lstSalida) {
                Row row = hoja.createRow(rowIdx++);

                Cell cel0 = row.createCell(0);
                cel0.setCellValue(obj.getIdLibro());
                cel0.setCellStyle(estiloNormalCentrado);

                Cell cel1 = row.createCell(1);
                cel1.setCellValue(obj.getTitulo());
                cel1.setCellStyle(estiloNormalIzquierda);

                Cell cel2 = row.createCell(2);
                cel2.setCellValue(obj.getAnio());
                cel2.setCellStyle(estiloNormalCentrado);

                Cell cel3 = row.createCell(3);
                cel3.setCellValue(obj.getSerie());
                cel3.setCellStyle(estiloNormalCentrado);

                Cell cel4 = row.createCell(4);
                cel4.setCellValue(obj.getCategoriaLibro().getDescripcion());
                cel4.setCellStyle(estiloNormalIzquierda);

                Cell cel5 = row.createCell(5);
                cel5.setCellValue(obj.getTipoLibro().getDescripcion() );
                cel5.setCellStyle(estiloNormalCentrado);
            }

            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-disposition", "attachment; filename=ReporteDocente.xlsx");

            OutputStream outStream = response.getOutputStream();
            excel.write(outStream);
            outStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (excel != null)
                    excel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    */

}
