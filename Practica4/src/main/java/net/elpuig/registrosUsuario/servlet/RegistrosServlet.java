package net.elpuig.registros.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.elpuig.registros.modelo.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrosServlet extends HttpServlet {

    private List<Usuario> list = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        super.init();
        list.add(new Usuario("Andrea", "andrea@gmail.com", "1234"));
        list.add(new Usuario("Elisa", "elisa@gmail.com", "5678"));
        list.add(new Usuario("Isa", "isa@gmail.com", "6789"));
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getServletPath(); // obtiene la url que activó el servlet

        if (op.equals("/registro")) {
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validación de campos vacíos
            if (nombre == null || nombre.isEmpty() ||
                    email == null || email.isEmpty() ||
                    password == null || password.isEmpty()) {

                request.setAttribute("error", "Por favor, rellena todos los campos.");
                request.getRequestDispatcher("/index.html").forward(request, response);
                return;
            }

            // Crear nuevo usuario y añadir a la lista
            Usuario nuevo = new Usuario(nombre, email, password);
            list.add(nuevo);

            // Pasar datos a JSP dentro de WEB-INF
            request.setAttribute("usuario", nuevo);
            request.setAttribute("listaUsuarios", list);

            request.getRequestDispatcher("/WEB-INF/resultado.jsp").forward(request, response);
        }
    }
}
