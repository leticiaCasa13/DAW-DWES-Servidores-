package net.elpuig.gestioestud.servlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.elpuig.gestioestud.dao.AlumnoDao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;   // NUEVO
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

import net.sf.jasperreports.engine.util.JRLoader;

import net.sf.jasperreports.export.*;

import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;



    public class Report extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            final AsyncContext ctxAsincrono = request.startAsync();

            ctxAsincrono.setTimeout(10000); // 10 segundos reales

            ctxAsincrono.addListener(new AsyncListener() {

                public void onComplete(AsyncEvent event) {
                    System.out.println("Informe generado");
                }

                public void onTimeout(AsyncEvent event) {
                    System.out.println("Tiempo excedido");
                }

                public void onError(AsyncEvent event) {
                    System.out.println("Error: " + event.getThrowable().getMessage());
                }

                public void onStartAsync(AsyncEvent event) {
                }
            });

            ctxAsincrono.start(() -> {

                try {

                    // ====================================================
                    // RUTAS
                    // ====================================================

                    String rutaJrxml = getServletContext()
                            .getRealPath("/WEB-INF/informes/alumnos/Alumnos.jrxml");

                    String rutaJasper = getServletContext()
                            .getRealPath("/WEB-INF/informes/alumnos/Alumnos.jasper");

                    File ficheroJrxml = new File(rutaJrxml);
                    File ficheroJasper = new File(rutaJasper);

                    // ====================================================
                    // SI NO EXISTE .JASPER -> COMPILAR AUTOMÁTICAMENTE
                    // ====================================================

                    if (!ficheroJasper.exists()) {

                        JasperCompileManager.compileReportToFile(
                                ficheroJrxml.getPath(),
                                ficheroJasper.getPath()
                        );

                        System.out.println("Informe compilado automáticamente");
                    }

                    // ====================================================
                    // CARGAR INFORME
                    // ====================================================

                    JasperReport jasperReport =
                            (JasperReport) JRLoader.loadObject(ficheroJasper);

                    // ====================================================
                    // CONEXIÓN BBDD
                    // ====================================================

                    AlumnoDao dao = new AlumnoDao();

                    try (Connection con = dao.getConnection()) {

                        JasperPrint jasperPrint = JasperFillManager.fillReport(
                                jasperReport,
                                new HashMap<String, Object>(),
                                con
                        );

                        // ====================================================
                        // FORMATO
                        // ====================================================

                        String tipoInforme = request.getParameter("optInformes");

                        response.setContentType(tipoInforme);

                        // PDF
                        if ("application/pdf".equals(tipoInforme)) {

                            JRPdfExporter exporter = new JRPdfExporter();

                            exporter.setExporterInput(
                                    new SimpleExporterInput(jasperPrint));

                            exporter.setExporterOutput(
                                    new SimpleOutputStreamExporterOutput(
                                            response.getOutputStream()));

                            exporter.exportReport();

                            // EXCEL
                        } else if ("application/vnd.ms-excel".equals(tipoInforme)) {

                            response.setHeader(
                                    "Content-Disposition",
                                    "inline; filename=informe.xls");

                            JRXlsExporter exporter = new JRXlsExporter();

                            exporter.setExporterInput(
                                    new SimpleExporterInput(jasperPrint));

                            exporter.setExporterOutput(
                                    new SimpleOutputStreamExporterOutput(
                                            response.getOutputStream()));

                            exporter.setConfiguration(
                                    new SimpleXlsReportConfiguration());

                            exporter.exportReport();

                            // WORD
                        } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                                .equals(tipoInforme)) {

                            response.setHeader(
                                    "Content-Disposition",
                                    "inline; filename=informe.docx");

                            JRDocxExporter exporter = new JRDocxExporter();

                            exporter.setExporterInput(
                                    new SimpleExporterInput(jasperPrint));

                            exporter.setExporterOutput(
                                    new SimpleOutputStreamExporterOutput(
                                            response.getOutputStream()));

                            exporter.exportReport();

                            // HTML
                        } else {

                            request.getSession().setAttribute(
                                    ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
                                    jasperPrint
                            );

                            HtmlExporter exporter = new HtmlExporter();

                            exporter.setExporterInput(
                                    new SimpleExporterInput(jasperPrint));

                            SimpleHtmlExporterOutput salidaHtml =
                                    new SimpleHtmlExporterOutput(
                                            response.getOutputStream());

                            salidaHtml.setImageHandler(
                                    new WebHtmlResourceHandler(
                                            "image?image={0}")
                            );

                            exporter.setExporterOutput(salidaHtml);

                            exporter.exportReport();
                        }
                    }

                } catch (JRException | IOException | SQLException e) {

                    e.printStackTrace();

                } finally {

                    ctxAsincrono.complete();
                }
            });
        }

}
