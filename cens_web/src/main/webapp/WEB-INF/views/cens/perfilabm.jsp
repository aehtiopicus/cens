<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/perfilabm.js"></script>

    	
<script>
var pagePath="<%=request.getContextPath()%>";
var miembroId=${miembroId};
</script>

<fieldset>
	
			<h3 class="subtitulo" >Datos de Usuario</h3>
			<div class="perfil">
				
				<div class="perfilheader" onclick="alert('hola')">
				</div>
				<div class="data">
					<div>
						<label for="username">Usuario:</label> 
						<input type="text" id="username" placeholder="Ingrese Nombre de Usuario" />
					</div>
					<div>
						<label for="perfilUsuario">Perfil:</label> 
						<span id="perfilUsuario" class="comments-link bold"></span>
					</div>
					<div>
						<label for="cambiarPass">Contrase&ntilde;a:</label> 
						<span id="cambiarPass" class="comments-link bold" style="text-transform: uppercase;">Cambiar contrase&ntilde;a</span>
					</div>				
				</div>
			</div>
			<h3 class="subtitulo" >Datos Personales</h3>
			<div>
				<label for="nombre">Nombre:</label>
				<input type="text" id="nombre" placeholder="Ingrese Nombre"/>
			</div>

			<div>
				<label for="apellido">Apellido:</label>
				<input type="text"  id="apellido" placeholder="Ingrese Apellido"/>
			</div>	
			
			<div>
				<label for="dni">DNI:</label>
				<input  type="text" id="dni" class="entero" maxlength="8" placeholder="Ingrese DNI sin puntos, ni comas"/>
			</div>
			<div>
				<label for="fechaNac">Fecha de Nacimiento:</label>
				<input  type="text"   id="fechaNac" class="hasdatepicker" maxlength="10" placeholder ="Ingrese Fecha de Nacimiento"/>
			</div>
					
			<div class="footerForm">
				<button class="button" type="button" onclick="guardarCartilla();">Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick=" location.href = document.referrer;">Cancelar</button>
			</div>
			

			<input type="hidden" id="fileUploadUsed" />
			<input type="hidden" id="id" />
			<input type="hidden" id="usuarioid" />						
			
</fieldset>

<div id="cambiarPassword" class="dialog" title="Cambio de Contrase&ntilde;a">
			<jsp:include page="resetpass.jsp"/>
	<div>
		<div id="progressbar" style="border: none;">
			<div class="progress-label"></div>
			<input type="hidden" id="fileUploadFailure" />
		</div>
	</div>
</div>

<div id="borrarCartilla" class="dialog" title="Confirmar">
	<p>Se eliminar&aacute; la cartilla adjunta. ¿Desea continuar?</p>

</div>
