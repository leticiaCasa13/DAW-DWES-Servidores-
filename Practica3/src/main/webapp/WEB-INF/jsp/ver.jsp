<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.elpuig.prac4a.javabeans.*,java.util.*"%>
<html>
<head>
<title>Ver</title>
<meta charset="UTF-8">
<link href="estilos.css" rel="stylesheet" type="text/css" />
<style>
table, td, th {
  border: 1px solid black;
  border-collapse: collapse;
  padding: 5px;
}
</style>
</head>
<body>
<div class="content">
<%String nombre = request.getParameter("nombre");%>
<h1>
Mensajes para <%=nombre%>
</h1>
<table>
<tr><th>Remitente</th><th>Mensaje</th></tr>
<%boolean men = false;
ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) request.getAttribute("mensajes");
if(mensajes!=null)
  //si existen mensajes para ese destinatario
  //se generará una tabla con los mismos
  for(int i=0;i<mensajes.size();i++){
    Mensaje m=(Mensaje)mensajes.get(i);
    if((m.getDestino()).equalsIgnoreCase(nombre)){
      men = true;%>
      <tr><td><%=m.getRemite()%></td><td><%=m.getTexto()%></td></tr>
    <%}
  }
if(!men){%>
    <!--si no hay mensajes se envía al usuario
    a la página nomensajes.jsp-->
    <jsp:forward page="/WEB-INF/jsp/nomensajes.jsp"/>
<%}%>
</table>
<br/><br/>
<a href="index.html">Inicio</a>
</div>
</body>
</html>