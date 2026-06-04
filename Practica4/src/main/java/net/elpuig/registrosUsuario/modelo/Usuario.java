package net.elpuig.registros.modelo;

public class Usuario {


    private String nombre;
    private String email;
    private String password;


    public Usuario(String nombre, String email, String contrasena) {
        this.nombre = nombre;
        this.email = email;
        this.password = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}


