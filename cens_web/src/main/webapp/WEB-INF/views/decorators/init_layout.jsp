<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<!DOCTYPE html>  
<html lang="es">  
    <head>  
    	<link type="text/css" href="<%=request.getContextPath() %>/css/init.css" rel="stylesheet" />
        <meta charset="utf-8" />  
        
        <title><decorator:title/></title>  
        
       	<meta content="True" name="HandheldFriendly">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="viewport" content="width=device-width">
       
    </head>
      
    <body marginheight="0px" marginwidth="0px" style="margin-top: 0px; margin-left: 0px;"> 
    	<div class="mainPanel">

    	
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