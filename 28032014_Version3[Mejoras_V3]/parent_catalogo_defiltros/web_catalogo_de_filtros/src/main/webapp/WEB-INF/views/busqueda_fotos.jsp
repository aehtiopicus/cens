 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
	
		<title>Catalogo de Filtros</title>
		
		<jsp:include page="includes/includes.jsp"></jsp:include>
		<script src="<%=request.getContextPath() %>/resources/js/v2/tablas/tablaArticulos.js"></script>
		<script src="<%=request.getContextPath() %>/resources/js/v2/imagenes/galeria.js"></script>
		<script src="<%=request.getContextPath() %>/resources/js/gridify/jquery.blockUI.js"></script>

				
		<script> 
			tituloEncabezado = "Catálogo de Filtros";
		</script>			
	</head>
	<body>
		<!-- Agrego el header a la pagina -->
		<jsp:include page="includes/header.jsp"></jsp:include>
		
		<div id=div_content>
			<div id=div_filtros>
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
					<button class=button onclick="loadImages(1)">Buscar</button>
					<button class=button onclick="limpiar()">Limpiar</button>
				</div>	
				<div id="empty" class="emptydiv">
				</div>				
			</div>
			
			<div id="content-fotos">
				<div id="items" class="tablefotos"></div>
				
				<div style="width: 100%;">
					<div style="width: 300px; margin-left: auto; margin-right: auto;">

  						<ul id="pagination"class="pagination-sm paginationfotos"></ul>
					</div>
				</div>
	
				<div id="notFound" style="display:none; text-align: center;"> 
					<span id="texto-vacio-fotos" class="texto-vacio-fotos">No se encontraron resultados.</span> 
				</div> 
			</div>
	
    </div>
    <!-- Agrego el footer a la pagina -->
	<jsp:include page="includes/footer.jsp"></jsp:include>
  </body>

</html>	


		
