<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.elpuig.registros.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%
    // Obtenemos el usuario recién registrado desde el servlet
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    // Obtenemos la lista de todos los usuarios (opcional)
    List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registro Exitoso</title>
    <style>
        body {
            font-family: Arial;
            background-color: #ccffcc;
            text-align: center;
            padding: 20px;
        }
        table {
            margin: 0 auto;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #666;
            padding: 8px 12px;
        }
        th {
            background-color: #99cc99;
        }
    </style>
</head>
<body>

<h1>Registro Exitoso</h1>

<p>Usuario <strong><%= usuario.getNombre() %></strong> registrado correctamente.</p>

<h2>Usuarios registrados hasta ahora:</h2>
<table>
    <tr>
        <th>Nombre</th>
        <th>Email</th>
        <th>Password</th>
    </tr>
    <%
        if (listaUsuarios != null) {
            for (Usuario u : listaUsuarios) {
    %>
    <tr>
        <td><%= u.getNombre() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getPassword() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<a href="index.html">Volver al formulario</a>

</body>
</html>





