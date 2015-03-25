<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/cens/notificacion.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/localstorage.js"></script>

<div id="cambiarEstado" class="dialog" title="Confirmar">
<p id="cambiarEstadoValue"></p>
<fieldset style="width: auto;">				
			<div class="passreset">
				<div>
					<label for="usuario.password">Contrase&ntilde;a Actual:</label>
					<input type="password" id="passwordOld" placeholder = "Ingrese contrase&ntilde;a"/>					
				</div>
				<div>
					<label for="usuario.password">Nueva Contrase&ntilde;a:</label>
					<input type="password" id="password" placeholder = "Ingrese contrase&ntilde;a"/>					
				</div>
				
				<div>
					<label for="usuario.passwordConfirm">Confirmar Contrase&ntilde;a:</label>
					<input type="password" id="passwordNewConfirm" placeholder ="Confirme contrase&ntilde;a"/>					 
				</div>
			</div>
</fieldset>	
</div>