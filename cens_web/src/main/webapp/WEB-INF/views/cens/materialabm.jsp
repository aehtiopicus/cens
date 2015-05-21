<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath()%>/js/cens/materialabm.js"></script>

<script src="<%=request.getContextPath()%>/js/cens/comentarios.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.timeago.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.autogrow-textarea.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.comment.js"></script>   
    	
<script>
var pagePath="<%=request.getContextPath()%>";
var profesorId = ${profesorId};
var programaId = ${programaId};
var nro = ${nro};
</script>

<fieldset>
	<div class="tituloForm">
	<c:choose>
		<c:when test="${disabled!=null && disabled == 'true'}">
			<h3 ><span style="color: rgb(218, 77, 44);"> MATERIAL DID&Aacute;CTICO NO EDITABLE</span></h3>
			<h3 id="titulo">Planificaci&oacute;n del Material Didactico para<span class="cursoFont"> ${asignatura}</span></h3>
		</c:when>
		<c:otherwise>
			<h3 id="titulo">Planificaci&oacute;n del Material Didactico para <span class="cursoFont"> ${asignatura}</span></h3>
		</c:otherwise>
	</c:choose>	
	</div>
	<c:choose>
		<c:when test="${disabled!=null && disabled == 'true'}">
			<div>
				<label for="nombre">Nombre:</label> <input type="text" id="nombre"
					placeholder="Nombre del material did&aacute;ctico" readonly/>
			</div>
			<div>
				<label for="descripcion" style="float: left;">Descripci&oacute;n:</label>
				<textarea rows="4" cols="50" id="descripcion"
					style="margin-left: 3px;" placeholder="Breve descripci&oacute;n" readonly></textarea>
			</div>
			<div>
				<label for="divisionPeriodoType">Semestre:</label>
				 <select id="divisionPeriodoType" disabled>			   
		        	 <c:forEach items="${division}" var="divisionType" >		         			         		
		         		<option value="${divisionType.divisionPeriodoType}" selected> ${divisionType.divisionPeriodoType.periodoName} </option>		         				         			         		           
		        	</c:forEach>
	        	</select>
			</div>
			
			<div id="cartillaAdjuntado" style="display:none;">
				<label for="downloadCartillaAdjunta">Cartilla Adjunta:</label>
				<div class="downloadFileDiv">
					<a id="downloadCartillaAdjunta" class="comments-link bold">No existe una cartilla adjunta</a>					
				</div>
			</div>
			

			<div class="footerForm">
				<button class="button" type="button" onclick="guardarCartilla();" disabled>Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick="window.location='<%=request.getContextPath()%>/mvc/profesor/asignaturaList'">Cancelar</button>
			</div>

			<input type="hidden" id="fileUploadUsed" />
			<input type="hidden" id="btnGuardarCartilla" />
			<input type="hidden" id="fileUploadComentarioUsed" />
			<c:if test="${materialId != null}">
				<input type="hidden" id="id" value="${materialId}" />
			</c:if>
		</c:when>
		<c:otherwise>
			<div>
				<label for="nombre">Nombre:</label> <input type="text" id="nombre"
					placeholder="Nombre del material did&aacute;ctico" />
			</div>
			<div>
				<label for="descripcion" style="float: left;">Descripci&oacute;n:</label>
				<textarea rows="4" cols="50" id="descripcion"
					style="margin-left: 3px;" placeholder="Breve descripci&oacute;n" ></textarea>
			</div>
			<div>
				<label for="divisionPeriodoType">Semestre:</label>
				 <select id="divisionPeriodoType">			   
		        	 <c:forEach items="${division}" var="divisionType">		         			         		
		         		<option value="${divisionType.divisionPeriodoType}" selected> ${divisionType.divisionPeriodoType.periodoName} </option>		         				         			         		           
		        	</c:forEach>
	        	</select>
			</div>
			
			<div id="cartillaAdjuntado" style="display:none;">
				<label for="downloadCartillaAdjunta">Cartilla Adjunta:</label>
				<div class="downloadFileDiv">
					<a id="downloadCartillaAdjunta" class="comments-link bold">No existe una cartilla adjunta</a>	
					<label id="eliminarCartillaAdjunto" class='eliminar-archivo' style='display:none;'></label>
				</div>
			</div>
			<archivos>
			<div id="fileUp">
				<label for="fileupload">Cartilla Adjunta:</label>
				<button class="button" type="button"
					style="height: 32px; top: -1px;">
					Insertar Archivo <input id="fileupload"
						title="Seleccionar Cartilla" type="file" class="custom-file-input"
						name="file"
						accept=".pttx,.ppt,.xlsx,.xls,.doc,.docx,.pps,.ppsx,.pdf" />
				</button>
				<input type="text" readonly="readonly" id="fileUploadName"
					style="width: 302px; margin-left: 7px;" />
			</div>


			</archivos>

			<div class="footerForm">
				<button class="button" type="button" onclick="guardarCartilla();">Guardar</button>

				<button id="cancelar" class="button" type="button"
					onclick=" location.href = document.referrer;">Cancelar</button>
			</div>

			<input type="hidden" id="fileUploadUsed" />
			<input type="hidden" id="btnGuardarCartilla" />
			<input type="hidden" id="fileUploadComentarioUsed" />
			<c:if test="${materialId != null}">
				<input type="hidden" id="id" value="${materialId}" />
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<h3 class="subtitulo" id="comentariosTitulo" style="display: none;">Comentarios</h3>
	
			<div id="accordion" style="height: 100%;">
				
			</div>
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
