<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.*, jakarta.servlet.http.*" %>
<html>
<head>
    <title>Envío</title>
    <meta charset="UTF-8">
    <link href="estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>



<div class="content">
    <h1>Generación de mensajes</h1>
    <form action ="grabar.go" method="post">
        <br/><br/>
        <b>Datos del mensaje</b><br/><br/>
        <hr/>
        <table>
            <tr>
                <td>Destinatario:</td>
                <td><input name="destinatario" size="40"></td>
            </tr>
            <tr>
                <td>Remitente:</td>
                <td><input name="remitente" size="40"></td>
            </tr>
            <tr>
                <td>Mensaje:</td>
                <td><textarea name="texto"></textarea></td>
            </tr>
        </table>
        <hr/><br/>
        <input type="submit" name="Submit" value="Enviar">
        <input type="reset" value="Reset">
    </form>
</div>

</body>
</html>
