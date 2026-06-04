package net.elpuig.gestioestud.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elpuig.gestioestud.Alumno;
import net.elpuig.gestioestud.Usuario;
import net.elpuig.gestioestud.dao.AlumnoDao;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GestionEstudiantesServlet extends HttpServlet {

    //  Lista persistente para simular la bbdd. Solo se guarda en memoria del servidor


    private AlumnoDao dao = new AlumnoDao();   //habla con bbdd



    private void incrementarContadorSesion(HttpSession sesion) {

        Integer contadorAccesos = 0;

        if (!sesion.isNew()) {
            Integer contadorActual = (Integer) sesion.getAttribute("contadorAccesos");

            if (contadorActual != null) {
                contadorAccesos = contadorActual + 1;
            }
        }

        sesion.setAttribute("contadorAccesos", contadorAccesos);
    }


    private String validarUsuario(String usuarioIntro, String passIntro) {

        if (usuarioIntro.equals("leticia") && passIntro.equals("leticia")) {
            return usuarioIntro;
        }

        return null;
    }



    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(true);
        incrementarContadorSesion(sesion);

        String op = request.getServletPath();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // info sesión

        if (op.equals("/info")) {
            request.getRequestDispatcher("/infosesion.jsp").forward(request, response);
            return;
        }


        // Desconectar

        else if (op.equals("/desconectar")) {
            sesion.invalidate();
            request.getRequestDispatcher("/desconectado.jsp").forward(request, response);
            return;
        }


        // validar login

        else if (op.equals("/validar")) {

            String usuarioIntro = request.getParameter("txtUsuario");
            String passIntro = request.getParameter("txtContrasenya");

            String user = validarUsuario(usuarioIntro, passIntro);

            if (user == null) {
                request.getRequestDispatcher("/errorlogin.jsp").forward(request, response);
                return;
            }

            // Guardamos el usuario validado en la sesión
            Usuario usu = new Usuario(user);
            sesion.setAttribute("usuario", usu);
            sesion.setAttribute("nombre", usu.getNombre());

            // Recuperar datos guardados del alumno
            int idGuardado = Integer.parseInt((String) sesion.getAttribute("sesId"));
            String cursoGuardado = (String) sesion.getAttribute("sesCurso");
            String nombreGuardado = (String) sesion.getAttribute("sesNombre");

            Alumno nuevo = new Alumno(idGuardado, cursoGuardado, nombreGuardado);

            int filas = dao.insertar(nuevo);

            // limpiamos datos guardados (opcional pero recomendable)
            sesion.removeAttribute("sesId");
            sesion.removeAttribute("sesCurso");
            sesion.removeAttribute("sesNombre");

            request.getRequestDispatcher("/oklogin.jsp").forward(request, response);
            return;
        }

        // listar alumnos

        else if (op.equals("/ejecutar")) {

            List<Alumno> lista = dao.obtenerTodos();

            out.println("<html>");
            out.println("<head><title>Resultados</title></head>");
            out.println("<body style='background-color:#ffffcc; font-family:Arial;'>");

            out.println("<h2 style='text-align:center; margin-top:40px;'>Lista de alumnos</h2>");
            out.println("<table style='margin: 20px auto; border-collapse: collapse; text-align:center;'>");

            out.println("<tr>");
            out.println("<th style='padding: 8px 20px;'>ID</th>");
            out.println("<th style='padding: 8px 20px;'>Curso</th>");
            out.println("<th style='padding: 8px 20px;'>Nombre</th>");
            out.println("</tr>");

            for (Alumno a : lista) {
                out.println("<tr>");
                out.println("<td style='padding: 6px 20px;'>" + a.getId() + "</td>");
                out.println("<td style='padding: 6px 20px;'>" + a.getCurso() + "</td>");
                out.println("<td style='padding: 6px 20px;'>" + a.getNombre() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
            return;
        }


        // alta

        else if (op.equals("/alta")) {

            String idStr = request.getParameter("id");
            String curso = request.getParameter("curso");
            String nombre = request.getParameter("nombre");

            // si no está logueado -> guardar datos y mandar al login
            if (sesion.getAttribute("usuario") == null) {

                sesion.setAttribute("sesId", idStr);
                sesion.setAttribute("sesCurso", curso);
                sesion.setAttribute("sesNombre", nombre);

                request.getRequestDispatcher("/acceso.jsp").forward(request, response);
                return;
            }

            // si está logueado -> insertar directamente
            int id = Integer.parseInt(idStr);
            Alumno nuevo = new Alumno(id, curso, nombre);

            int filas = dao.insertar(nuevo);

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
            return;
        }
    }

}
