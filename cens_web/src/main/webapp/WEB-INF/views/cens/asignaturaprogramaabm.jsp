<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath() %>/js/cens/profesorasignaturaprograma.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.fileupload.js"></script>


		<fieldset>			
			<div class="tituloForm" style="display: none;">
						<h3 id="titulo">Alta de Crusos</h3>									
			</div>			

			<div>
				<label for="nombre">Nombre del Programa:</label>
				<input type="text" id="nombre"/>
			</div>
			<div>
				<label for="descripcion">Descripci&oacute;n del Programa:</label>
				<textarea rows="4" cols="50" id="descripcion"></textarea>
			</div>								
			<div>
				<label for="cantCartillas">Cantidad de Cartillas:</label>
				<input type="text" id="cantCartillas" maxlength="2" class="entero"/>
			</div>
			<div id="fileUp">
			<label for="fileupload">Adjuntar Programa:</label>
				<input id="fileupload" type="file" name="files[]" data-url="upload" multiple>
				<div id="dropzone" class="fade well">Drop files here</div>
 
    			<div id="progress" class="progress">
        			<div class="bar" style="width: 0%;"></div>
    			</div>
    			<h5 style="text-align:center"><i style="color:#ccc"><small>Max File Size: 2 Mb - Display last 20 files</small></i></h5>
 
    			<table id="uploaded-files" class="table">
        			<tr>
            			<th>Nombre del archivo:</th>
            			<th>Tama&ntilde;o del archivo:</th>
            			<th>Tipo de archivo:</th>
            			<th>Download</th>
            			<th>Uploaded By</th>
        			</tr>
    			</table>
			</div>
			
			<div class="footerForm">
				<button class="button" type="button" onclick="submitCurso('<%=request.getContextPath() %>/curso');" >Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/cursoList'">Cancelar</button>
			</div>
		</fieldset>

