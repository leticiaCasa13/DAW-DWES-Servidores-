<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>Sin mensajes</title>
<meta charset="UTF-8">
<link href="estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <div class="content">
    <h2>
     Lo siento <%=request.getParameter("nombre")%>, no tiene mensajes
    </h2>
    <br/><br/><br/><br/>
    <a href="index.html">Inicio</a>
  </div>
</body>
</html>