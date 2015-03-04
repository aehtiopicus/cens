<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="<%=request.getContextPath()%>/js/cens/contactoabm.js"></script>
  
    	
<script>
var pagePath="<%=request.getContextPath()%>";
 var miembroId = ${miembroId};
// var programaId = ${programaId};
// var nro = ${nro};
$(document).ready(function(){
	
			
	
});
</script>

<fieldset>
		
			<h3 class="subtitulo" >Informaci&oacute;n de contacto</h3>
			<div>
				<label for="email">Email:</label>
				<input type="text" id="email" placeholder="cens@cens.com"/>
			</div>
			<input type="hidden" id="emailOriginal"/>
			<input type="hidden" id="emailId" />
			<input type="hidden" id="fileUploadUsed" />
			<input type="hidden" id="btnGuardarCartilla" />
			
			<h3 class="subtitulo" >Redes sociales</h3>
			
</fieldset>

<div id="guardarCartilla" class="dialog" title="Confirmar">
	<p>Se adjuntar&aacute; una cartilla al programa. ¿Desea
		continuar?</p>
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
