<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Acceso</title>
</head>
<body style="font-family:Arial; background-color:#ffffcc;">

<h2>Acceso restringido</h2>


<!-- envía los datos -->

<form action="/Gestion-Estudiantes/validar" method="post">
    <label>Usuario:</label>
    <input type="text" name="txtUsuario" required>
    <br><br>

    <label>Password:</label>
    <input type="password" name="txtContrasenya" required>
    <br><br>

    <button type="submit">Aceptar</button>
</form>

</body>
</html>