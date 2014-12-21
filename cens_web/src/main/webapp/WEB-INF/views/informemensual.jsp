<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<script type="text/javascript" src="<%=request.getContextPath() %>/js/browserdetect.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.handsontable.full.js"></script>	

	<link rel="stylesheet" media="screen" href="<%=request.getContextPath() %>/css/jquery.handsontable.full.css">

    <script src="<%=request.getContextPath() %>/js/informemensualexcel.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
		<!-- Filtros -->
		<div class="filtrosDiv">
			<h3>Informe Mensual</h3>

<!-- 			<div> -->
				<label for="cliente">Cliente:</label>
				<input type="text" value="${informeHeaderDto.nombreCliente}" disabled="disabled" />
<!-- 			</div> -->
<!-- 			<div> -->
				<label for="periodo">Periodo:</label>
				<input type="text" value="${informeHeaderDto.periodo}" disabled="disabled" />			
<!-- 			</div>			 -->
			
			<input type="hidden" value="${informeHeaderDto.informeMensualId}" id="idInforme"/>
	   </div>	

        
		<!-- Tabla excel -->
		<div class="excelGridContainer handsontable"></div>
		<div class="messageDiv">
        	<label id="message"></label>
        </div>
		
		<div class="accionesGrillaExcel">
			<button class="button" type="button" onclick="javascript:botonVolver();">Volver</button>
			<button class="button" type="button" onclick="javascript:exportar();">Exportar a Excel</button>
    		<c:if test="${informeHeaderDto.estado == 'Borrador'}">
	    		<button class="button" type="button" onclick="javascript:save();">Guardar</button>
	    		<button class="button" type="button" onclick="javascript:save(1);">Guardar y Volver</button>
	    		<button class="button" type="button" onclick="javascript:save_dialog();">Guardar y Enviar</button>    		
    		</c:if>
    	</div>
    </div>

</div>

<div id="enviarDialog" class="dialog" title="Confirmar">
	<p>El informe no podrá ser editado en el futuro. ¿Desea continuar?</p>
</div>

<div id="spiner">
	<div class="spiner_image"></div>
	<span class="spiner_text_informe">Cargando Informe...</span>
</div>
