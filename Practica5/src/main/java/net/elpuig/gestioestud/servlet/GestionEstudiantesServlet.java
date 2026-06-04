package net.elpuig.gestioestud.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elpuig.gestioestud.Alumno;
import net.elpuig.gestioestud.dao.AlumnoDao;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GestionEstudiantesServlet extends HttpServlet {

    //  Lista persistente para simular la bbdd. Solo se guarda en memoria del servidor


    private AlumnoDao dao = new AlumnoDao();   //habla con bbdd



    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getServletPath(); // obtiene la url que activó el servlet

        response.setContentType("text/html;charset=UTF-8");  // indica que la respuesta será HTML
        PrintWriter out = response.getWriter();

        // operación q tenemos q ejecutar.  con slash
        if (op.equals("/ejecutar")) {

            List<Alumno> lista = dao.obtenerTodos();

            // mostramos la lista de alumnos actualizada
            out.println("<html>");
            out.println("<head><title>Resultados</title></head>");
            out.println("<body style='background-color:#ffffcc; font-family:Arial;'>");

            out.println("<h2 style='text-align:center; margin-top:40px;'>Lista de alumnos</h2>");
            out.println("<table style='margin: 20px auto; border-collapse: collapse; text-align:center;'>");

            // Cabecera de la tabla
            out.println("<tr>");
            out.println("<th style='padding: 8px 20px;'>ID</th>");
            out.println("<th style='padding: 8px 20px;'>Curso</th>");
            out.println("<th style='padding: 8px 20px;'>Nombre</th>");
            out.println("</tr>");

            // Filas con los alumnos    //se crea la variable "a" dentro del for each, para mostrar los objetos de la lista
            for (Alumno a : lista) {
                out.println("<tr>");
                out.println("<td style='padding: 6px 20px;'>" + a.getId() + "</td>");
                out.println("<td style='padding: 6px 20px;'>" + a.getCurso() + "</td>");
                out.println("<td style='padding: 6px 20px;'>" + a.getNombre() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        }


            // alta alumno
            if (op.equals("/alta")) {
                                       //recoge los valores que el usuario a puesto en el formulario
                int id = Integer.parseInt(request.getParameter("id"));
                String curso = request.getParameter("curso");
                String nombre = request.getParameter("nombre");



                // ⭐ Añadir a la lista que se mantiene

                Alumno nuevo = new Alumno(id, curso, nombre);

                //Guardar en BD

                int filas = dao.insertar(nuevo);


                // bckg amarillo
                out.println("<html>");
                out.println("<head><title>Alta</title></head>");
                out.println("<body style='background-color:#ffffcc; font-family:Arial;'>");


                if (filas == 1) {
                    out.println("<h2>Alumno insertado correctamente</h2>");
                    out.println("<p>" + id + " - " + curso + " - " + nombre + "</p>");
                } else {
                    out.println("<h2 style='color:red'>ERROR al insertar en la base de datos</h2>");
                }

                out.println("</body></html>");
        }
    }
}