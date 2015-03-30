<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/cens/notificacion.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/localstorage.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/notificacionBuilder.js"></script>

<div id="notificacionDeUsuario" class="dialog" title="Notificaciones">
		
<div id="tabs" style="border: none;">
  	<ul style="background: transparent; border-top: transparent;border-left: transparent;border-right: transparent; display:none">
    	<li><a href="#notificacionDeUsuarioData">Notificaciones</a></li>
    	<li><a href="#seguimientoActvidad" id="segAct">Seguimiento de Actividad</a></li>
    	
  	</ul>
	<div id="notificacionDeUsuarioData"></div>
	<div id="seguimientoActvidad" style="display:none;"></div>			
</div>
	
</div>
