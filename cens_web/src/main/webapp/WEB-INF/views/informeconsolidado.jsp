<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<script type="text/javascript" src="<%=request.getContextPath() %>/js/browserdetect.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.handsontable.full.js"></script>	

	<link rel="stylesheet" media="screen" href="<%=request.getContextPath() %>/css/jquery.handsontable.full.css">

<%--     <script src="<%=request.getContextPath() %>/js/concurrent.thread.js"></script> --%>
    <script src="<%=request.getContextPath() %>/js/informeconsolidadoexcel.js?v=<%= new Date().getTime()%>"></script>

<div class="centreDiv">
  
    <div class="floatLeftDiv">
    
		<!-- Filtros -->
		<div class="filtrosDiv">
			<h3>Informe Consolidado</h3>

<!-- 			<div> -->
				<label for="periodo">Periodo:</label>
				<input type="text" value="${informeHeaderDto.periodo}" disabled="disabled" />			
<!-- 			</div>			 -->

				<div class="consolidado-search">
					<label for="filtro">Buscar empleado por apellido o legajo:</label>
					<input type="text" id="txtFiltro" style="width: 300px!important;"/>			
					<button class="button" type="button" onclick="applyFilter()">Buscar</button>
				</div>
			
			<input type="hidden" value="${informeHeaderDto.informeConsolidadoId}" id="idInforme"/>
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
	    		<button class="button" type="button" onclick="javascript:save_dialog();">Guardar y Finalizar</button>    		
    		</c:if>
    	</div>
    </div>

</div>

<div id="finalizarDialog" class="dialog" title="Confirmar">
	<p>El informe no podrá ser editado en el futuro. ¿Desea continuar?</p>
</div>

<div id="adicionalesDialog" class="dialog" title="Detalle de Adicionales">
	<p>Adicional 1: xxx</p>
	<p>Adicional 2: xxx</p>
	<p>Adicional 3: xxx</p>
</div>

<c:if test="${informeHeaderDto.estado == 'Borrador'}">
	<div id="parametros">
		<input type="hidden" id="topeRetencion" value="${topeRetencion}">
		<input type="hidden" id="retOSPorc" value="${retOSPorc}">
		<input type="hidden" id="ret11Porc" value="${ret11Porc}">
		<input type="hidden" id="ret3Porc" value="${ret3Porc}">
		<input type="hidden" id="diasPeriodo" value="${diasPeriodo}">
		<input type="hidden" id="horasPeriodo" value="${horasPeriodo}">		
		<input type="hidden" id="divisorVacaciones" value="${divisorVacaciones}">		
		<input type="hidden" id="contCodigos" value="${contCodigos}">		
		<input type="hidden" id="contOsPorc" value="${contOsPorc}">		
	</div>
</c:if>

<div id="spiner">
	<div class="spiner_image"></div>
	<span class="spiner_text_informe">Cargando Informe...</span>
</div>