<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script>var pagePath="<%=request.getContextPath() %>"</script>
	<script>var asesorId="${asesorId}"</script>
    <script src="<%=request.getContextPath() %>/js/cens/asesordashboard.js"></script>
	<script>var socialPublish = new asesorSocialDashboard.social.publish();</script>
  	<div id="yearContent"></div>
  	
  <div id="publishDialog" class="dialog" title="Publicaci&oacute;n de contenido">
	<h3 class="subtitutlo" style="text-align: -webkit-left;">Contenido a publicar > Estado: <span id="publishState" class="estadoToken"></span></h3>

	<div style="margin-top : 10px;">
		<h3 class="subtitutlo" style="text-align: -webkit-left;">Mensaje: </h3>
		<textarea rows="4" cols="50" style="margin-left: 3px; width:100%;" id="publishMessage"></textarea>			
	</div>
	<div style="margin-top : 10px;">
		<button class="button" type="button" id="publishSubmit">Publicar</button>
		<button class="button" type="button" id="publishDelete">Eliminar Publicaci&oacute;n</button>
	</div>
</div>



