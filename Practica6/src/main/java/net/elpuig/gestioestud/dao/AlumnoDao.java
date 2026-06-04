package net.elpuig.gestioestud.dao;
import net.elpuig.gestioestud.Alumno;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDao {

    //  Carga manual del driver MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static final String URL =
            "jdbc:mysql://10.50.50.131:3306/dbAlumnos" +
                    "?useSSL=false" +
                    "&serverTimezone=UTC" +
                    "&allowPublicKeyRetrieval=true";


    private static final String USER = "daw2";
    private static final String PASS = "Secreto123!";

//Obtener todos los alumnos (inicio del programa)

    public List<Alumno> obtenerTodos()  {

        List<Alumno> lista = new ArrayList<>();

        String sql = "SELECT id, curso, nombre FROM alumnos";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())  {

            while (rs.next()) {
                Alumno a = new Alumno(
                        rs.getInt("id"),
                        rs.getString("curso"),
                        rs.getString("nombre")
                );
                lista.add(a);
            }
        }catch (SQLException e) {
            e.printStackTrace();

        }

        return lista;

    }

    // Insertar alumno (alta)

    public int insertar(Alumno a) {

        String sql = "INSERT INTO alumnos (id, curso, nombre) VALUES (?, ?, ?)";  //agregar id

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {



            ps.setInt(1, a.getId());         //id
            ps.setString(2, a.getCurso());
            ps.setString(3, a.getNombre());

            return  ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }



}
