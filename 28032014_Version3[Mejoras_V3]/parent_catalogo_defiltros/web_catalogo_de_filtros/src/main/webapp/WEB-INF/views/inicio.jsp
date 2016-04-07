 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<title>Catalogo de Filtros</title>
		<jsp:include page="includes/includes.jsp"></jsp:include>
                <script src="<%=request.getContextPath() %>/resources/js/v2/update/updateType.js"></script>
                <script src="<%=request.getContextPath() %>/resources/js/v2/inicio.js"></script>
	</head>
	<body id=inicio-page onload="setTimeout(function() {window.scrollTo(0, 1)}, 100)">
		<div id=container-principal>
			<div id=logo-empresa>
				<span id="imagen-logo"></span>
			</div>
			<div id=container-inicio>
				<div id=header-busquedda> 
					<label>Seleccione el tipo de búsqueda</label>
				</div>
				<div id="button" class="left">
					<button  onclick="javascirpt:location.href='<%=request.getContextPath() %>/buscarPorFiltro'">Búsqueda por Número o Tipo</button>
				</div>
				<div id="button" class="fotos">
					<button onclick="javascirpt:location.href='<%=request.getContextPath() %>/buscarPorFotos'">Búsqueda Por Fotos</button>
				</div>
				<div id="button" class="right">
					<button onclick="javascirpt:location.href='<%=request.getContextPath() %>/buscarPorAplicacion'">Búsqueda Por Aplicación</button>
				</div>
			</div>
		</div>
                                <div id="actualizarDialog" class="dialog" title="Actualizaci&oacute;n del Sistema">
                           
                         
                            
                           <div id="messageDiv">
                               <span id="message">Existen actualizaciones</span>
                               <div id="updateTypeDiv">                                   
                               </div>
                               <span id="updateTypeWarning" style="color: chocolate; font-weight: 900;font-size: 10px;">Existen actualizaciones para descargar</span>
                           </div>
								
			</div>
                                <div id="spiner">
			       <div class="loading ui-state-default ui-state-active loadingpopupactualizacion">Comprobando versión...</div> 
<!-- 				<div class="spiner_image"></div> -->
<!--  				<span class="spiner_text">Actualizando..</span> -->
<!--  				<span class="spiner_text2">Finalizado, Volviendo a pantalla principal.</span> -->
			</div>
	</body>
	<script type="text/javascript">
	$(function() {
	    $( "button"  )
	      .button()
	      .click(function( event ) {
	        event.preventDefault();
	      });
	    });
            
	setCookie('empresa', ${empresa}); 
	setCookie('porcentajeVenta', ${porcentaje});
	setCookie('fechaActualizacion', "${fechaActualizacion}");
	setCookie('appPath', location.origin + "<%=request.getContextPath() %>/");        
	

		delCookie('c1');
		delCookie('c2');
		delCookie('c3');
		delCookie('c4');
		delCookie('c5');
		delCookie('c6');
		delCookie('ac1');
		delCookie('ac2');
		delCookie('ac3');
	</script>
</html>