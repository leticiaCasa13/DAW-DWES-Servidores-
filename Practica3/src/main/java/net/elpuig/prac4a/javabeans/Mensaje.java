package net.elpuig.prac4a.javabeans;

public class Mensaje {

        private String remite;
        private String destino;
        private String texto;

        public Mensaje() {
        }

        // constructor que permite crear un objeto
        // Mensaje a partir de los datos del mismo
        public Mensaje(String remite, String destino, String texto) {
            this.remite = remite;
            this.destino = destino;
            this.texto = texto;
        }

        public void setRemite(String remite) {
            this.remite = remite;
        }

        public String getRemite() {
            return this.remite;
        }

        public void setDestino(String destino) {
            this.destino = destino;
        }

        public String getDestino() {
            return this.destino;
        }

        public void setTexto(String texto) {
            this.texto = texto;
        }

        public String getTexto() {
            return this.texto;
        }


}
