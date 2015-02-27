<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="<%=request.getContextPath()%>/js/cens/materialabm.js"></script>
  
    	
<script>
var pagePath="<%=request.getContextPath()%>";
// var profesorId = ${profesorId};
// var programaId = ${programaId};
// var nro = ${nro};
$(document).ready(function(){
	$('#email').
	inputmask({
		mask : "*{1,20}@*{1,20}[.*{1,5}]",
		regex : "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
		 isComplete: function(buffer, opts) {
		        return new RegExp(opts.regex).test(buffer.join(''));
		    }
	});
		
			
	
});
</script>

<fieldset>
	<div class="tituloForm">

			
	
	</div>			
			<h3 class="subtitulo" >Informaci&oacute;n de contacto</h3>
			<div>
				<label for="email">Email:</label>
				<input type="text" id="email" placeholder="cens@cens.com"/>
			</div>
			

			<div class="footerForm">
				<button class="button" type="button" onclick="guardarCartilla();">Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick=" location.href = document.referrer;">Cancelar</button>
			</div>
			

			<input type="hidden" id="fileUploadUsed" />
			<input type="hidden" id="btnGuardarCartilla" />
			
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
