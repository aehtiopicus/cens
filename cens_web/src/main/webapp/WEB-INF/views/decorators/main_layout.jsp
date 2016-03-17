<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>  
<html lang="es">  
    <head>  
<!--         <meta charset="utf-8" />   -->
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="0">
		<meta content="True" name="HandheldFriendly">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="viewport" content="width=device-width">
        
        <title><decorator:title/></title>  

        <link type="text/css" href="<%=request.getContextPath() %>/css/layout.css" rel="stylesheet" /> 
        <link href="<%=request.getContextPath() %>/css/midasUI-theme/jquery-ui-1.10.3.custom.css" rel="stylesheet">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/chosen.css">


		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery.fileupload.css">    
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jqgrid/ui.jqgrid.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/slick/slick.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery.comment.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/notify/notify-flat.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/ttw-notification-menu/style_light.css"/>
        
        <script src="<%=request.getContextPath() %>/js/jquery-1.10.1.min.js"></script>
		<script src="<%=request.getContextPath() %>/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="<%=request.getContextPath() %>/js/jquery.numeric.js" type="text/javascript"></script>
		<script src="<%=request.getContextPath() %>/js/jquery.jqGrid.min.js" type="text/javascript"></script>     
		<script src="<%=request.getContextPath() %>/js/jquery.ui.datepicker-es.js"></script>     
    	<script src="<%=request.getContextPath() %>/js/grid.locale-en.js" type="text/javascript"></script>	    
	    <script src="<%=request.getContextPath() %>/js/grid.locale-es.js" type="text/javascript"></script>
	    <script type="text/javascript" src="<%=request.getContextPath() %>/js/slick.min.js"></script>	    
	    <script src="<%=request.getContextPath() %>/js/chosen.jquery.js"></script>
        <script src="<%=request.getContextPath() %>/js/cookies.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath() %>/js/jquery.inputmask.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath() %>/js/utils.js" type="text/javascript"></script>
        
        <script src="<%=request.getContextPath()%>/js/cens/responsivemenu.js"></script>            
        <script src="<%=request.getContextPath() %>/js/cens/layout/mainlayout.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath() %>/js/notify.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath() %>/js/xls.js" type="text/javascript"></script>   
        
            
		<script>
		var mainPagePath ="<%=request.getContextPath() %>";
		</script>

    </head>
      
    <body marginheight="0px" marginwidth="0px" style="margin-top: 0px; margin-left: 0px;">    	
    	<div class="mainPanel" id="mainPanel" style="display: none;">
    		<div class="headerPanel">
    			<!-- INSERTAR HEADER.JSP -->
    			<jsp:include page="includes/header.jsp"></jsp:include>
    		</div>
    	
    		<div class="menuPanel" id="menuPanel">
    			<!-- INSERTAR MENU.JSP -->
    			<sec:authorize access="hasRole('ROLE_ADMINISTRADOR') AND hasRole('ROLE_ASESOR')">
    				<jsp:include page="includes/god_menu.jsp"></jsp:include>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ASESOR') AND !hasRole('ROLE_PROFESOR') AND !hasRole('ROLE_ADMINISTRADOR')">
	    			<jsp:include page="includes/asesoria_menu.jsp"></jsp:include>
				</sec:authorize>
					<sec:authorize access="hasRole('ROLE_PROFESOR') AND hasRole('ROLE_ASESOR') AND !hasRole('ROLE_ADMINISTRADOR')">
	    			<jsp:include page="includes/profesor_asesor_menu.jsp"></jsp:include>
				</sec:authorize>
    			<sec:authorize access="hasRole('ROLE_PROFESOR') AND !hasRole('ROLE_ASESOR') AND !hasRole('ROLE_ADMINISTRADOR')">
	    			<jsp:include page="includes/profesor_menu.jsp"></jsp:include>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ALUMNO')">
	    			<jsp:include page="includes/alumno_menu.jsp"></jsp:include>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_PRECEPTOR') AND !hasRole('ROLE_ADMINISTRADOR')">
	    			<jsp:include page="includes/preceptor_menu.jsp"></jsp:include>
				</sec:authorize>

				
    		</div>
    	
    		<div class="contentPanel" id="contentPanel">
    			<!-- INSERTAR BODY -->
    			<decorator:body/>  
    		</div>
    		<div class="modal">
    			<div style="height: 100vh;text-align: -webkit-center;vertical-align: text-top;width: 100vw;">
    			<span style="position: absolute; top: 40%; height: 2em;margin-top: 3em;margin-left: -80px; font-size: 17px;color: #005577;">Cargando Informaci&oacute;n</span>
    			</div>
    		</div>
    		<div id="responsiveMenu">
    			<jsp:include page="includes/menu_responsive.jsp"></jsp:include>
    		</div>
    		<div class="footerPanel">
    			<!-- INSERTAR FOOTER.JSP -->    		
    			<jsp:include page="includes/footer.jsp"></jsp:include>
    		</div>
    	</div>

    </body>  
</html> 