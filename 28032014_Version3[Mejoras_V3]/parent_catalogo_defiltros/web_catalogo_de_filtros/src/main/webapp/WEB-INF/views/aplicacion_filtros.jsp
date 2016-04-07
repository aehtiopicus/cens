 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<title>Catalogo de Filtros - Filtros Aplicacion</title>
		<jsp:include page="includes/includes.jsp"></jsp:include>
		<script>
			var idAplicacion = ${aplicacion.data[0].id}; 
			tituloEncabezado = '${aplicacion.data[0].marca} - ${aplicacion.data[0].modelo}';
		</script>
		<script src="<%=request.getContextPath()%>/resources/js/v2/tablas/tablaFiltrosAplicacion.js"></script>
		
	</head>
	<body>
		<!-- Agrego el header a la pagina -->
		<jsp:include page="includes/header.jsp"></jsp:include>

		<div id="content" class="aplicaciones-filtro">
			<div class="filtros-aire-column inline">
				<table id="filtros-aire-table"></table>
			</div><div class="filtros-aceite-column inline">
				<table id="filtros-aceite-table"></table>
			</div><div class="filtros-combustible-column inline">
				<table id="filtros-combustible-table"></table>
			</div><div class="filtros-habitaculo-column inline">
				<table id="filtros-habitaculo-table"></table>
			</div><div class="filtros-otros-column inline">
				<table id="filtros-otros-table"></table>
			</div>
		</div>
		<!-- Agrego el footer a la pagina -->
		<jsp:include page="includes/footer.jsp"></jsp:include>	
	</body>
</html>