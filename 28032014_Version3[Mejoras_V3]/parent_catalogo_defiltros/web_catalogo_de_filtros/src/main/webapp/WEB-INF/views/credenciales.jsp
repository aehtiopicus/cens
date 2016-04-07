<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<script src="resources/js/jquery-1.9.1.min.js"></script>
<link rel=stylesheet type="text/css"
	href='resources/css/clean_forms.css' />
<title>Activaci&oacute;n Requerida</title>


</head>
<body>
	<div class="container" >

		<form method="GET"  action="activation/${codigo}"
			class="rounded" style="width: 80%; margin: auto;" >
			<h3 style="text-align: center;">Formulario de activaci&oacute;n del Sistema</h3>

			<div class="field">
				<label >Identificador de equipo:</label>
				<input class="input" disabled="disabled" value="${codigo}" style="width: 60%;"/>
				<br/>
			</div>
			<div class="field">
				<label >Contacto: </label>
				<label style="text-align: left;">mail@representante.com</label>
				<br/>
			</div>
				
			<input type="submit" value="Activar" class="button" />
		</form>
	</div>
</body>
</html>