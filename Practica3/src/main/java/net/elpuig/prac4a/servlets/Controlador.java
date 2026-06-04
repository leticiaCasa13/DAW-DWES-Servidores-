package net.elpuig.prac4a.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import net.elpuig.prac4a.javabeans.*;
import net.elpuig.prac4a.modelo.*;

public class Controlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getServletPath();

        // Acceso a la página de envío de mensajes
        if (op.equals("/envio.go")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/envio.jsp");
            rd.forward(request, response);

            // Grabar mensaje enviado
        } else if (op.equals("/grabar.go")) {
            String remitente = request.getParameter("remitente");
            String destinatario = request.getParameter("destinatario");
            String texto = request.getParameter("texto");

            Mensaje men = new Mensaje(remitente, destinatario, texto);
            Operaciones oper = new Operaciones();
            oper.grabaMensaje(men);

            response.sendRedirect("index.html");

            // Acceso a la página para introducir nombre del usuario y ver mensajes
        } else if (op.equals("/muestra.go")) {
            response.sendRedirect("mostrar.html");

            // Mostrar los mensajes del usuario
        } else if (op.equals("/ver.go")) {
            String nombre = request.getParameter("nombre");

            Operaciones oper = new Operaciones();
            ArrayList<Mensaje> mensajes = oper.obtenerMensajes(nombre);

            request.setAttribute("mensajes", mensajes);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ver.jsp");
            rd.forward(request, response);
        }
    }
}
