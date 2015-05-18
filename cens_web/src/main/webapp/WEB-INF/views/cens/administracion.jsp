<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script>
	var pagePath="<%=request.getContextPath() %>";
		var oauth2;
	</script>	
    <script src="<%=request.getContextPath() %>/js/cens/admin.js"></script>
    <script>
    var oauthCompletedData ={
    		oauth_status :"${oauth != null ?oauth : undefined}",
    			provider:"${provider!= null ?provider : undefined}",
    			code:"${code!= null ?code : undefined}",
    			error_code: "${error_code!= null ?error_code : undefined}"
    };    
    </script>
  	<div id="adminContent" class="admin">
  		<div id="socialPortlet" class="censaccordion curso">
  			
  			<h3 class="subtitulo" style="text-align: -webkit-left;">Autenticaci&oacute;n con Redes Sociales</h3>
  			<div id="fbDiv" class="portlet ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
  				<div class="portlet-header ui-widget-header ui-corner-all" id="portletHeader"><div>
  					<label class="fbIcon-unhovered"></label>
  					<label class ="fbHeaderLabel">Facebook</label>
  					</div> 
  				</div>
  				<div>  			
  					<div class="estadoAutenticacion">
						<h3 class="subtitulo" style="text-align: -webkit-left;">Estado de Autenticaci&oacute;n > Token: <span class="estadoToken inexistente" id="tokenFacebook">Activo</span></h3>
					
						<div>
							<label>Clave de cliente</label>
							<input type="text" id="key">
						</div>  	  						
						<div>
							<label>Identificaci&oacute;n de cliente</label>
							<input type="text" id="secret">
						</div>
						<div style="text-align: center;">
							
							<button class="button" type="button" id="fbAutenticar">Autenticar</button>
							<button class="button" type="button" id="fbRemover">Remover Token</button>
						</div>
  					</div> 					
  				</div>
  			</div>
  		</div>
  	</div>
  <div id="loginDialog" class="dialog" title="Autenticaci&oacute;n">
	<p>Autenticaci&oacute;n de Usuario ante sistema externo</p>
	<div>
		<label id="fbLink" for="fbLinkButton">Facebook</label>			
	</div>
</div>

<div id="successLoginDialog" class="dialog" title="Autenticaci&oacute;n">
	<p>Token de acceso concedido</p>
	<div>
		<label class="fbIcon-unhovered"></label>
  		<label class ="fbHeaderLabel">Facebook</label>			
	</div>
</div>