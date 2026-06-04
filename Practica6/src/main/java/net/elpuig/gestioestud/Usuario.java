package net.elpuig.gestioestud;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;




    public class Usuario implements HttpSessionBindingListener {

        private String nombre;


        @Override
        public String toString() {
            return nombre;
        }

        public Usuario(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public void valueBound(HttpSessionBindingEvent event) {

            System.out.println("Usuario añadido a sesión: " + event.getSession().getId());

            ServletContext contexto = event.getSession().getServletContext();

            synchronized (contexto) {

                Integer usuariosValidados =
                        (Integer) contexto.getAttribute("usuariosValidados");

                if (usuariosValidados == null) {
                    usuariosValidados = 0;
                }

                usuariosValidados++;

                contexto.setAttribute("usuariosValidados", usuariosValidados);
            }
        }

        @Override
        public void valueUnbound(HttpSessionBindingEvent event) {

            System.out.println("Usuario eliminado de sesión: " + event.getSession().getId());

            ServletContext contexto = event.getSession().getServletContext();

            synchronized (contexto) {

                Integer usuariosValidados =
                        (Integer) contexto.getAttribute("usuariosValidados");

                if (usuariosValidados == null) {
                    usuariosValidados = 0;
                }

                usuariosValidados--;

                if (usuariosValidados < 0) {
                    usuariosValidados = 0;
                }

                contexto.setAttribute("usuariosValidados", usuariosValidados);
            }
        }


   }
