<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="jakarta.servlet.ServletContext" %>

<%
    ServletContext contexto = getServletContext();
    Integer usuariosConectados = 0;
    Integer usuariosValidados = 0;

    synchronized (contexto) {
        if (contexto.getAttribute("usuariosConectados") != null)
            usuariosConectados = (Integer) contexto.getAttribute("usuariosConectados");

        if (contexto.getAttribute("usuariosValidados") != null)
            usuariosValidados = (Integer) contexto.getAttribute("usuariosValidados");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Información de Sesión</title>
</head>

<body style="font-family:Arial; background-color:#ffffcc;">

<h2>Información de la sesión</h2>

<table border="1" cellpadding="10">

    <tr>
        <td><b>Identificador</b></td>
        <td><%= session.getId() %></td>
    </tr>

    <tr>
        <td><b>Fecha/hora creación</b></td>
        <td><%= new Date(session.getCreationTime()) %></td>
    </tr>

    <tr>
        <td><b>Hora último acceso</b></td>
        <td><%= new Date(session.getLastAccessedTime()) %></td>
    </tr>

    <tr>
        <td><b>Número de accesos previos</b></td>
        <td><%= session.getAttribute("contadorAccesos") %></td>
    </tr>

    <tr>
        <td><b>Usuario</b></td>
        <td><%= (String)session.getAttribute("nombre") %></td>
    </tr>


    <tr>
        <td>Usuarios conectados</td>
        <td><%= usuariosConectados %></td>
    </tr>

    <tr>
        <td>Usuarios validados</td>
        <td><%= usuariosValidados %></td>
    </tr>

</table>

<br>
<a href="index.html">Volver</a>

</body>
</html>