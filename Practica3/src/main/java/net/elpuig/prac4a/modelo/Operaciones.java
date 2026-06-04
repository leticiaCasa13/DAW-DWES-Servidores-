package net.elpuig.prac4a.modelo;

import net.elpuig.prac4a.javabeans.*;
import java.util.*;

    public class Operaciones {

        private static ArrayList<Mensaje> mensajes=
                new ArrayList<Mensaje>();

        public synchronized ArrayList<Mensaje> obtenerMensajes(String destino) {

            ArrayList<Mensaje> mensajesEncontrados=null;

            if (mensajes.size()>0) {
                for (Mensaje m:mensajes) {
                    if (destino.equalsIgnoreCase(m.getDestino())) {
                        if (mensajesEncontrados==null)
                            mensajesEncontrados=new ArrayList<Mensaje>();

                        mensajesEncontrados.add(m);
                    }
                }
            }

            return mensajesEncontrados;
        }

        public synchronized void grabaMensaje(Mensaje m) {
            mensajes.add(m);
        }

}
