<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel=stylesheet type="text/css"
	href='<%= request.getContextPath() %>/resources/css/clean_forms.css' />
<script src="<%= request.getContextPath() %>/resources/js/jquery-1.9.1.min.js"></script>
<script src="<%= request.getContextPath() %>/resources/js/vendor/jquery.ui.widget.js"></script>
<script src="<%= request.getContextPath() %>/resources/js/jquery.iframe-transport.js"></script>
<script src="<%= request.getContextPath() %>/resources/js/jquery.fileupload.js"></script>
<script src="<%= request.getContextPath() %>/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath() %>/resources/js/catalogodefiltros/uploadFunction.js"></script>
<title>Volcado de Informaci&oacute;n</title>
</head>

<body>
	<div class="container">

		<form method="POST" action="activation/${codigo}" class="rounded">
			<h3>Formulario de activaci&oacute;n del Sistema</h3>


			<div class="field">

				<label>Archivo de Actualizaci&oacute;n</label>
				<div style="display: none;">
					<input id="fileuploadvolcado" type="file" name="files[]"
						data-url="<%= request.getContextPath() %>/fileUpload/upload/dump" title="Seleccione un archivo"
						accept="text/dump" />
				</div>
				<input type="button" class="fileChooser"
					value="Seleccione un archivo" onclick="fileUpload();" />
			</div>
			<div class="field">
				<label>Progreso de Carga:</label>
				<div>
					<div id="error" style="display: none;">
						<span class="error"> </span>
					</div>
					<div class="ui-progress-bar ui-container transition"
						id="progress_bar" style="width: 60%; float: left;">
						<div class="ui-progress" style="width: 0%; display: none;">
							<span class="ui-label" style="display: none;">Finalizado</span>
						</div>
					</div>
				</div>
			</div>


		</form>
	</div>
</body>
<body>
	<div class="activation"></div>
	<div class="modal"><!-- Place at bottom of page --></div>
</body>
<script>
	function fileUpload() {
		$('#fileuploadvolcado').click();
	}	
</script>
</html>