<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath() %>/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath() %>/js/cens/asignaturaprogramaabm.js"></script>
<script>
var pagePath="<%=request.getContextPath() %>";
var profesorId=${profesorId};
var asignaturaId=${asignaturaId};
</script>

		<fieldset>			
			<div class="tituloForm" >
						<h3 id="titulo">Plan de estudio para la asignatura</h3>									
			</div>			
			<div>
				<label for="nombre">Nombre:</label>
				<input type="text" id="nombre" placeholder="Nombre del programa"/>
			</div>
			<div>
				<label for="descripcion" style="float:left;">Descripci&oacute;n:</label>
				<textarea rows="4" cols="50" id="descripcion" style="margin-left: 3px;" placeholder="Breve descripci&oacute;n"></textarea>
			</div>								
			<div>
				<label for="cantCartillas">Cantidad de Cartillas:</label>
				<input type="text" id="cantCartillas" maxlength="2" class="entero"/>
			</div>
			<div>
				<label for="programaadjunto">Programa Adjunto:</label>
				<input type="text" id="programaadjunto" readonly="readonly" placeholder="No existe archivo" onclick="alert('hola');"/>
			</div>
			<archivos>
			<div id="fileUp">
				<label for="fileupload">Adjuntar Programa:</label>			
				<button class="button" type="button" style="height: 32px;top: -1px;">Agregar Archivo                                        
                	   <input id="fileupload" type="file" class="custom-file-input" name="file" multiple/>
                </button>
                <input type="text" readonly="readonly" id="fileUploadName" style="width:305px;" accept=".pttx,.ppt,.xlsx,.xls,.doc,.docx,.pps,.ppsx,pdf"/>
                <input type="hidden" id="fileUploadUsed"/>
                <input type="hidden" id="btnGuardarPrograma"/>
            </div>
           
				 			
			</archivos>
		
			<div class="footerForm">
				<button class="button" type="button" onclick="guardarPrograma();" >Guardar</button>

				<button id ="cancelar" class="button" type="button" onclick="window.location='<%=request.getContextPath() %>/mvc/cursoList'">Cancelar</button>
			</div>
		</fieldset>
		
<div id="guardarPrograma" class="dialog" title="Confirmar">
	<p>Se adjuntar&aacute; un programa a la asignatura. ¿Desea continuar?</p>
	 <div>
           <div id="progressbar" style="border: none;">
        		<div class="progress-label"></div>
    		</div>
    </div>
</div>
