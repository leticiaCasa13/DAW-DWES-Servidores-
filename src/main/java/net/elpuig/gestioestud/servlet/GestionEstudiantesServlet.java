package net.elpuig.gestioestud.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elpuig.gestioestud.Alumno;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GestionEstudiantesServlet extends HttpServlet {

    //  Lista persistente para simular la bbdd. Solo se guarda en memoria del servidor

    private List<Alumno> lista = new ArrayList<>();

    // Inicializamos la lista solo una vez cuando se arranca el servlet
    @Override
    public void init() throws ServletException {
        super.init();
        lista.add(new Alumno(1, "Java", "jose"));
        lista.add(new Alumno(2, "Python", "jordi"));
        lista.add(new Alumno(3, "PHP", "silvia"));
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getServletPath(); // obtiene la url que activó el servlet

        response.setContentType("text/html;charset=UTF-8");  // indica que la respuesta será HTML
        PrintWriter out = response.getWriter();

        // operación q tenemos q ejecutar.  con slash
        if (op.equals("/ejecutar")) {

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
                String idTexto = request.getParameter("id");
                String curso = request.getParameter("curso");
                String nombre = request.getParameter("nombre");

                // Evitar NumberFormatException
                if (idTexto == null || idTexto.isEmpty()) {   // si el usuario no rellena el campo, el servlet le asinga un valor "0",para evitar el error de exception
                    idTexto = "0";
                }

                int id = Integer.parseInt(idTexto);

                // ⭐ Añadir a la lista que se mantiene
                lista.add(new Alumno(id, curso, nombre));

                // bckg amarillo
                out.println("<html>");
                out.println("<head><title>Alta</title></head>");
                out.println("<body style='background-color:#ffffcc; font-family:Arial;'>");

                out.println("<h2 style='text-align:center;'>Filas afectadas: 1</h2>");

                out.println("<p style='text-align:center;'>Alumno insertado → "
                        + id + " - " + curso + " - " + nombre + "</p>");

                out.println("</body></html>");
        }
    }
}