package net.elpuig.clubdeportivo.modelo;

public class Socio {

    private int id ;
    private String nombre ;
    private String deporte;


    public Socio(int id, String nombre, String deporte) {
        this.id = id;
        this.nombre = nombre;
        this.deporte = deporte;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDeporte() {
        return deporte;
    }
}


