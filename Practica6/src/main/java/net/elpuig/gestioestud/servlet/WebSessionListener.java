package net.elpuig.gestioestud.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class WebSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent arg0) {

        System.out.println("Session creada");

        ServletContext contexto = arg0.getSession().getServletContext();

        synchronized (contexto) {

            Integer usuariosConectados =
                    (Integer) contexto
                            .getAttribute("usuariosConectados");
            if (usuariosConectados == null) {
                usuariosConectados = Integer.valueOf(0);
            }
            usuariosConectados+=1;
            contexto
                    .setAttribute("usuariosConectados",
                            usuariosConectados);

        } // synchronized

    }

    public void sessionDestroyed(HttpSessionEvent arg0) {

        System.out.println("Session destruida");
        ServletContext contexto =
                arg0.getSession().getServletContext();

        synchronized (contexto) {

            Integer usuariosConectados =
                    (Integer) contexto
                            .getAttribute("usuariosConectados");

            if (usuariosConectados == null) {
                usuariosConectados = Integer.valueOf(0);
            }
            usuariosConectados-=1;
            contexto
                    .setAttribute("usuariosConectados",
                            usuariosConectados);

        } // synchronized

    }





}
