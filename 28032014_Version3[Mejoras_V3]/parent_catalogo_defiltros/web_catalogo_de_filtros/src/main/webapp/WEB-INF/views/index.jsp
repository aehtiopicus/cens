 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<title>Catalogo de Filtros</title>
		
		<jsp:include page="includes/includes.jsp"></jsp:include>

		<script src="<%=request.getContextPath() %>/resources/js/v2/tablas/tablaArticulos.js"></script>
				
		<script> 
			tituloEncabezado = "Cat�logo de Filtros";
		</script>			
	</head>
	<body>
		<!-- Agrego el header a la pagina -->
		<jsp:include page="includes/header.jsp"></jsp:include>
		
		<div id=div_content>
			<div id=div_filtros>
				
				<div id=filtroByCodigo>
					<label for="codigo">C&oacute;digo:</label>
					<input type="text" id="codigoInput" name="codigoInput" placeholder="Referencia" onkeyup="javascript:busquedaPorCodigo();">
				</div>

				<div id=filtros>
					<div id=filtrosLeft class=filtrosPanel>
						<div class="filtro">
							<label for="marcaFiltro" class="labelForSelect">Marca Filtro:</label>
							<select class="chosen-select" id="marcaFiltroSelect">
								<option value=""></option>
							</select>
							<div id="marcaFiltro_loading" class="combo_loading"></div>
						</div>
						<div class="filtro">
							<label for="tipoFiltro" class="labelForSelect">Tipo Filtro:</label>
							<select class="chosen-select" id="tipoFiltroSelect">
								<option value=""></option>
							</select>						
							<div id="tipoFiltro_loading" class="combo_loading"></div>
						</div>
						<div class="filtro">
							<label for="subtipoFiltro" class="labelForSelect">Subtipo Filtro:</label>
							<select class="chosen-select" id="subTipoFiltroSelect">
								<option value=""></option>
							</select>						
							<div id="subTipoFiltro_loading" class="combo_loading"></div>
						</div>
					</div>
					<div id=filtrosRight class=filtrosPanel>
						<div class="filtro" id="tipoAplicacionFiltro">
							<label for="tipoAplicacion" class="labelForSelect">Tipo Aplicaci&oacute;n:</label>
							<select class="chosen-select" id="tipoAplicacionSelect">
								<option value=""></option>
							</select>
							<div id="tipoAplicacion_loading" class="combo_loading"></div>
						</div>
						<div class="filtro" id="marcaAplicacionFiltro">
							<label for="marcaAplicacion" class="labelForSelect">Marca Aplicaci&oacute;n:</label>
							<select class="chosen-select" id="marcaAplicacionSelect">
								<option value=""></option>
							</select>
							<div id="marcaAplicacion_loading" class="combo_loading"></div>
						</div>
						<div class="filtro" id="modeloAplicacionFiltro">
							<label for="modeloAplicacion" class="labelForSelect">Modelo Aplicaci&oacute;n:</label>
							<select class="chosen-select" id="modeloAplicacionSelect">
								<option value=""></option>
							</select>
							<div id="modeloAplicacion_loading" class="combo_loading"></div>
						</div>
					</div>
				</div>			

				<div id=filtrosBotones>
					<button class=button onclick="gridReload()">Buscar</button>
					<button class=button onclick="limpiar()">Limpiar</button>
				</div>					
			</div>
			
			<div id="div_tablaArticulos">
	           <table id="tablaArticulos"></table>
	           <div id="tablaArticulos_paging"></div>
	        </div>
			
		</div>	
		<!-- Agrego el footer a la pagina -->
		<jsp:include page="includes/footer.jsp"></jsp:include>
	</body>
</html>