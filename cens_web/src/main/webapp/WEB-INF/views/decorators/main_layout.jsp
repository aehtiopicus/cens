<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>  
<!DOCTYPE html>  
<html lang="es">  
    <head>  
<!--         <meta charset="utf-8" />   -->
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="0">
        
        <title><decorator:title/></title>  

        <link type="text/css" href="<%=request.getContextPath() %>/css/layout.css" rel="stylesheet" /> 
        <link href="<%=request.getContextPath() %>/css/midasUI-theme/jquery-ui-1.10.3.custom.css" rel="stylesheet">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/chosen.css">

        <script src="<%=request.getContextPath() %>/js/jquery-1.10.1.min.js"></script>
		<script src="<%=request.getContextPath() %>/js/jquery-ui-1.10.3.custom.min.js"></script>     
		<script src="<%=request.getContextPath() %>/js/jquery.ui.datepicker-es.js"></script>     
    	<script src="<%=request.getContextPath() %>/js/grid.locale-en.js" type="text/javascript"></script>
	    <script src="<%=request.getContextPath() %>/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	    <script src="<%=request.getContextPath() %>/js/grid.locale-es.js" type="text/javascript"></script>
	    <script src="<%=request.getContextPath() %>/js/jquery.numeric.js" type="text/javascript"></script>
	    <script src="<%=request.getContextPath() %>/js/chosen.jquery.js"></script>

        <script src="<%=request.getContextPath() %>/js/cookies.js" type="text/javascript"></script>

		<script>
	        var marginWidthGrid = 20;
    	    var marginHeightGrid = 370;
    	    var marginHeightGridFull = 345;

    	    $(document).ready(function() {
				$( ".button" ).button();
				
				$( ".datepicker" ).datepicker({
					inline: true,
					yearRange: "-100:+0",
					changeMonth: true,
				    changeYear: true
				});
				
				$( ".menu" ).menu({
	                position: {at: "left bottom"}
	            });
		        
				$( ".chosen-select" ).chosen({
					allow_single_deselect: true
				});
				
		        $(".entero").numeric();
		        $(".decimal").numeric(","); 
		        $(".decimalConPunto").numeric("."); 
		        
    	    });

    	    function deleteAllCookies() {
    	        var cookies = document.cookie.split(";");

    	        for (var i = 0; i < cookies.length; i++) {
    	        	var cookie = cookies[i];
    	        	var eqPos = cookie.indexOf("=");
    	        	var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
    	        	document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    	        }
    	    }
    	    
   	    
    	    function logout(){
    	    	deleteAllCookies();
    	    	window.location = '<%=request.getContextPath() %>/j_spring_security_logout';
    	    }
		</script>
    </head>
      
    <body marginheight="0px" marginwidth="0px" style="margin-top: 0px; margin-left: 0px;"> 
    	<div class="mainPanel">
    		<div class="headerPanel">
    			<!-- INSERTAR HEADER.JSP -->
    			<jsp:include page="includes/header.jsp"></jsp:include>
    		</div>
    	
    		<div class="menuPanel">
    			<!-- INSERTAR MENU.JSP -->
    			<security:authorize ifAllGranted="ROLE_ADMINISTRADOR">
	    			<jsp:include page="includes/asesoria_menu.jsp"></jsp:include>
				</security:authorize>
    			<security:authorize ifAllGranted="ROLE_RRHH">
	    			<jsp:include page="includes/menu_rrhh.jsp"></jsp:include>
				</security:authorize>
    			<security:authorize ifAnyGranted="ROLE_GTEOPERACION, ROLE_JEFEOPERACION">
	    			<jsp:include page="includes/menu_gte_operacion.jsp"></jsp:include>
				</security:authorize>
    			<security:authorize ifAllGranted="ROLE_ADMINISTRACION">
	    			<jsp:include page="includes/menu_administracion.jsp"></jsp:include>
				</security:authorize>
				
    		</div>
    	
    		<div class="contentPanel">
    			<!-- INSERTAR BODY -->
    			<decorator:body/>  
    		</div>
    	
    		<div class="footerPanel">
    			<!-- INSERTAR FOOTER.JSP -->    		
    			<jsp:include page="includes/footer.jsp"></jsp:include>
    		</div>
    	</div>
    	
    </body>  
</html> 