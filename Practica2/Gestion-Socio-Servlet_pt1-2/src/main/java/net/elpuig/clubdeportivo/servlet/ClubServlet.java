package net.elpuig.clubdeportivo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elpuig.clubdeportivo.modelo.Socio;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ClubServlet extends HttpServlet {

    //lista persistencia

    private List<Socio> lista = new ArrayList<>();

    //inicializamos la lista solo una vez, cuando se arranca el servlet

    public void init() throws ServletException{
        super.init();
        lista.add(new Socio (1, "Juan", "Fútbol"));
        lista.add(new Socio (2, "Jose", "Beisbol"));
        lista.add(new Socio (3, "Fran", "Ténis"));

    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


      String opera=request.getServletPath(); //guarda el path de la url q activó el servlet

      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out =response.getWriter();

      if (opera.equals("/ejecutar")){


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
          for (Socio s : lista) {
              out.println("<tr>");
              out.println("<td style='padding: 6px 20px;'>" + s.getId() + "</td>");
              out.println("<td style='padding: 6px 20px;'>" + s.getDeporte() + "</td>");
              out.println("<td style='padding: 6px 20px;'>" + s.getNombre() + "</td>");
              out.println("</tr>");
          }

          out.println("</table>");
          out.println("</body></html>");

      }

      //alta socio

        if(opera.equals("/alta")){

           // String idTexto = request.getParameter("id");
           // String deporte = request.getParameter("deporte");
           // String nombre = request.getParameter("nombre");

            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("deporte");
            String deporte = request.getParameter("nombre");

            lista.add(new Socio(id, deporte, nombre));


            // bckg amarillo
            out.println("<html>");
            out.println("<head><title>Alta</title></head>");
            out.println("<body style='background-color:#ffffcc; font-family:Arial;'>");

            out.println("<h2 style='text-align:center;'>Filas afectadas: 1</h2>");

            out.println("<p style='text-align:center;'>Alumno insertado → "
                    + id + " - " + deporte + " - " + nombre + "</p>");

            out.println("</body></html>");



        }


    }

}
